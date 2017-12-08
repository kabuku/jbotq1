/*
 * MIT License
 *
 * Copyright (c) 2017 Kabuku Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.github.soundtricker.androidthings.jbotq1

import android.content.SharedPreferences
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.google.android.things.pio.PeripheralManagerService
import nz.org.winters.android.libpca9685.PCA9685
import nz.org.winters.android.libpca9685.PCA9685Servo

class Jbot(private val mHandler: Handler, private val mHandlerThread: HandlerThread?, private val pref: SharedPreferences) {
    companion object {
        private val TAG = Jbot::class.java.simpleName
        private const val ALL_MATRIX = 9
        private const val ALL_SERVOS = 8
        private const val PWMRES_MIN = 100
        private const val PWMRES_MAX = 480
        private const val SERVO_MIN = 1
        private const val SERVO_MAX = 180
        private const val BASE_DELAY_TIME = 10 // 10ms

        // Servo channel matrix
        //
        //  ----          ----
        // | 04 |        | 00 |
        //  ---- -------- ----
        //      | 05  01 |
        //      |        |
        //      | 06  02 |
        //  ---- -------- ----
        // | 07 |        | 03 |
        //  ----          ----
        private const val CHANNEL_FRONT_LEFT_LEG = 4
        private const val CHANNEL_FRONT_RIGHT_LEG = 0
        private const val CHANNEL_BACK_LEFT_LEG = 7
        private const val CHANNEL_BACK_RIGHT_LEG = 3
        private const val CHANNEL_FRONT_LEFT_BODY = 5
        private const val CHANNEL_FRONT_RIGHT_BODY = 1
        private const val CHANNEL_BACK_LEFT_BODY = 6
        private const val CHANNEL_BACK_RIGHT_BODY = 2

        private val CHANNEL_SERVO_ORDER = intArrayOf(
                CHANNEL_FRONT_RIGHT_LEG,
                CHANNEL_FRONT_RIGHT_BODY,
                CHANNEL_BACK_RIGHT_BODY,
                CHANNEL_BACK_RIGHT_LEG,
                CHANNEL_FRONT_LEFT_LEG,
                CHANNEL_FRONT_LEFT_BODY,
                CHANNEL_BACK_LEFT_BODY,
                CHANNEL_BACK_LEFT_LEG
        )

        private val SERVO_ACT_0 = intArrayOf(135, 45, 135, 45, 45, 135, 45, 135, 500)
        private val SERVO_ACT_1 = intArrayOf(135, 45, 135, 45, 45, 135, 45, 135, 500)
    }

    val tweakAngle: IntArray

    private val mPca9685Servo: PCA9685Servo
    private var mCurrentServoProgram: JbotServoProgram? = null
    private var mCurrentServosPosition: IntArray = SERVO_ACT_0.copyOf()

    init {
        val peripheralManagerService = PeripheralManagerService()
        val tweakAngleStr = pref.getString("tweakAngle", "0,0,0,0,0,0,0,0")
        this.tweakAngle = tweakAngleStr.split(",").map { it.toInt() }.toIntArray()
        this.mPca9685Servo = PCA9685Servo(PCA9685.PCA9685_ADDRESS, peripheralManagerService)
        this.mPca9685Servo.setServoMinMaxPwm(SERVO_MIN, SERVO_MAX, PWMRES_MIN, PWMRES_MAX)
        zeroPosition()
    }

    fun zeroPosition() {
        mHandler.post {
            mCurrentServosPosition = SERVO_ACT_0.copyOf()
            for ((index, angle) in mCurrentServosPosition.withIndex()) {
                if (index >= ALL_SERVOS) {
                    continue
                }
                setServoAngle(index, angle)
                Thread.sleep(10)
            }
            mCurrentServoProgram = null
        }
    }

    fun centerPosition() {
        mHandler.post {
            mCurrentServosPosition = SERVO_ACT_1.copyOf()
            for ((index, angle) in mCurrentServosPosition.withIndex()) {
                if (index >= ALL_SERVOS) {
                    continue
                }
                setServoAngle(index, angle)
                Thread.sleep(10)
            }
            mCurrentServoProgram = null
        }
    }

    fun runProgram(program: JbotServoProgram) {
        mHandler.post {
            mCurrentServoProgram = program
            for (currentProgram in program.servoProgramLine) {
                runProgramLine(currentProgram)
            }
        }

    }

    fun runProgram(programLine: IntArray) {
        mHandler.post {
            runProgramLine(programLine)
        }
    }

    fun setTweakAngle(servoIndex: Int, tweakAngle: Int) {
        Log.d(TAG, "Set $servoIndex tweak angle to $tweakAngle")
        this.tweakAngle[servoIndex] = tweakAngle
        pref.edit().putString("tweakAngle", this.tweakAngle.joinToString(",")).apply()
    }

    fun setServoAngle(servoIndex: Int, angle: Int) {
        val channel = CHANNEL_SERVO_ORDER[servoIndex]
        val tweakedAngle = angle + tweakAngle[servoIndex]
        Log.d(TAG, "Set $servoIndex angle to $tweakedAngle ($angle)")
        mPca9685Servo.setServoAngle(channel, tweakedAngle)
    }

    fun close() {
        mHandlerThread?.quitSafely()
        mPca9685Servo.close()
    }

    private fun runProgramLine(currentProgram: IntArray) {
        val interTotalTime = currentProgram[ALL_MATRIX - 1]
        val interDelayCounter = interTotalTime / BASE_DELAY_TIME
        for (interStepLoop in 0 until interDelayCounter) {
            for (servoIndex in 0 until ALL_SERVOS) {
                val currentPosition = mCurrentServosPosition[servoIndex]
                val toPosition = currentProgram[servoIndex]

                if (currentPosition == toPosition) {
                    // NOP
                } else if (currentPosition > toPosition) {
                    val currentMovePosition = map((BASE_DELAY_TIME * interStepLoop).toLong(), 0L, interTotalTime.toLong(), 0L, (currentPosition - toPosition).toLong())
                    if (currentPosition - currentMovePosition.toInt() >= toPosition) {
                        setServoAngle(servoIndex, currentPosition - currentMovePosition.toInt())
                    }
                } else if (currentPosition < toPosition) {
                    val currentMovePosition = map((BASE_DELAY_TIME * interStepLoop).toLong(), 0L, interTotalTime.toLong(), 0L, (toPosition - currentPosition).toLong())
                    if (currentPosition + currentMovePosition <= toPosition) {
                        setServoAngle(servoIndex, currentPosition + currentMovePosition.toInt())
                    }
                }
            }
            Thread.sleep(BASE_DELAY_TIME.toLong())
        }
        mCurrentServosPosition = currentProgram.copyOf()
    }

    private fun map(x: Long, inMin: Long, inMax: Long, outMin: Long, outMax: Long): Long =
            (x - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

}