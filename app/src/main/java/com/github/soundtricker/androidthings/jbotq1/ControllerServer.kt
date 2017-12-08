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

import android.content.Context
import android.util.Log
import fi.iki.elonen.NanoHTTPD

class ControllerServer(private val context: Context, private val jbot: Jbot, port: Int = 8080) : NanoHTTPD(port) {
    companion object {
        private val TAG = ControllerServer::class.java.simpleName
    }

    init {
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false)
    }

    override fun serve(session: IHTTPSession?): Response {

        if (session != null) {
            try {
                when (session.uri) {
                    "/" -> return handleIndex(session)
                    "/save" -> return handleSave(session)
                    "/controller" -> return handleController(session)
                    "/editor" -> return handleEditor(session)
                    "/zero" -> return handleZero(session)
                    "/setting" -> return handleSetting(session)
                    "/online" -> return handleOnline(session)
                }

            } catch (t: Throwable) {
                Log.e(TAG, "get error", t)
                throw t
            }
        }

        return super.serve(session)
    }


    private fun handleOnline(session: IHTTPSession): Response {
        val m0 = session.parms["m0"]!!.toInt()
        val m1 = session.parms["m1"]!!.toInt()
        val m2 = session.parms["m2"]!!.toInt()
        val m3 = session.parms["m3"]!!.toInt()
        val m4 = session.parms["m4"]!!.toInt()
        val m5 = session.parms["m5"]!!.toInt()
        val m6 = session.parms["m6"]!!.toInt()
        val m7 = session.parms["m7"]!!.toInt()
        val t1 = session.parms["t1"]!!.toInt()

        val programLine = intArrayOf(m0, m1, m2, m3, m4, m5, m6, m7, t1)
        jbot.runProgram(programLine)
        return newFixedLengthResponse("(m0, m1)=($m0,$m1)")
    }


    private fun handleIndex(session: IHTTPSession): Response {
        val inputStream = context.resources.openRawResource(R.raw.index)
        val contents = inputStream.bufferedReader().readLines().joinToString("\n")
        return newFixedLengthResponse(contents)
    }

    private fun handleController(session: IHTTPSession): Response {
        val pm = session.parms["pm"]
        val servo = session.parms["servo"]

        if (!pm.isNullOrEmpty()) {
            if (pm!!.toInt() == 100) {
                jbot.zeroPosition()
            } else {
                jbot.runProgram(JbotServoProgram.values()[pm.toInt() - 1])
            }
        }
        if (!servo.isNullOrEmpty()) {
            val servoIndex = servo!!.toInt()
            val angle = session.parms["value"]
            jbot.setServoAngle(servoIndex, angle!!.toInt())
        }

        return newFixedLengthResponse("(pm)=($pm) (servo)=($servo)")
    }

    private fun handleSave(session: IHTTPSession): Response {

        val key = session.parms["key"]
        val value = session.parms["value"]

        val keyInt = Integer.parseInt(key)
        val valueInt = Integer.parseInt(value)

        if (keyInt == 100) {
            for (i in 0 until 8) {
                jbot.setTweakAngle(i, 0)
            }
        } else {
            if (valueInt >= -124 && valueInt <= 124) {
                jbot.setTweakAngle(keyInt, valueInt)
            }
        }
        return newFixedLengthResponse("(key, value)=($key,$value)")
    }

    private fun handleSetting(session: IHTTPSession): Response {
        val inputStream = context.resources.openRawResource(R.raw.setting)
        var contents = inputStream.bufferedReader().readLines().joinToString("\n")
        for (key in arrayOf("4", "0", "5", "1", "6", "2", "7", "3")) {

            contents = contents.replace(Regex("__" + key + "__"), session.parms.getOrDefault(key, jbot.tweakAngle[key.toInt()].toString()))
        }
        return newFixedLengthResponse(contents)
    }

    private fun handleZero(session: IHTTPSession): Response {
        val inputStream = context.resources.openRawResource(R.raw.zero)
        val contents = inputStream.bufferedReader().readLines().joinToString("\n")
        return newFixedLengthResponse(contents)
    }

    private fun handleEditor(session: IHTTPSession): Response {
        val inputStream = context.resources.openRawResource(R.raw.editor)
        val contents = inputStream.bufferedReader().readLines().joinToString("\n")
        return newFixedLengthResponse(contents)
    }

}