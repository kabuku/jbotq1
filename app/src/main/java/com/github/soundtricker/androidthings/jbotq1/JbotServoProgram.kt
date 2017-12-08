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

enum class JbotServoProgram(val servoProgramLine: Array<IntArray>) {

    WAIT(arrayOf(
            intArrayOf(90, 90, 90, 90, 90, 90, 90, 90, 500),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 500)
    )),
    GO_FORWARD(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(90, 90, 90, 110, 110, 90, 45, 90, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 45, 70, 200),
            intArrayOf(70, 90, 90, 90, 90, 90, 45, 70, 200),
            intArrayOf(70, 45, 135, 90, 90, 90, 90, 70, 200),
            intArrayOf(70, 45, 135, 110, 110, 90, 90, 70, 200),
            intArrayOf(90, 90, 135, 110, 110, 90, 90, 90, 200),
            intArrayOf(90, 90, 90, 110, 110, 135, 90, 90, 200),
            intArrayOf(70, 90, 90, 110, 110, 135, 90, 70, 200),
            intArrayOf(70, 90, 90, 110, 90, 135, 90, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200)
    )),
    GO_BACK(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(90, 45, 90, 110, 110, 90, 90, 90, 200),
            intArrayOf(70, 45, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(70, 45, 90, 90, 90, 90, 90, 70, 200),
            intArrayOf(70, 90, 90, 90, 90, 135, 45, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 135, 45, 70, 200),
            intArrayOf(90, 90, 90, 110, 110, 135, 90, 90, 200),
            intArrayOf(90, 90, 135, 110, 110, 90, 90, 90, 200),
            intArrayOf(70, 90, 135, 110, 110, 90, 90, 70, 200),
            intArrayOf(70, 90, 135, 90, 110, 90, 90, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200)
    )),
    GO_LEFT(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(70, 90, 90, 90, 90, 45, 90, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 45, 90, 70, 200),
            intArrayOf(90, 90, 90, 110, 110, 45, 90, 90, 200),
            intArrayOf(90, 90, 45, 110, 110, 90, 135, 90, 200),
            intArrayOf(70, 90, 45, 110, 110, 90, 135, 70, 200),
            intArrayOf(70, 90, 90, 90, 90, 90, 135, 70, 200),
            intArrayOf(70, 135, 90, 90, 90, 90, 90, 70, 200),
            intArrayOf(70, 135, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(90, 135, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200)
    )),
    TURN_LEFT(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(90, 90, 90, 110, 110, 90, 90, 90, 200),
            intArrayOf(90, 135, 90, 110, 110, 90, 135, 90, 200),
            intArrayOf(70, 135, 90, 110, 110, 90, 135, 70, 200),
            intArrayOf(70, 135, 90, 90, 90, 90, 135, 70, 200),
            intArrayOf(70, 135, 135, 90, 90, 135, 135, 70, 200),
            intArrayOf(70, 135, 135, 110, 110, 135, 135, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200)
    )),
    TURN_RIGHT(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200),
            intArrayOf(70, 90, 90, 90, 90, 90, 90, 70, 200),
            intArrayOf(70, 90, 45, 90, 90, 45, 90, 70, 200),
            intArrayOf(70, 90, 45, 110, 110, 45, 90, 70, 200),
            intArrayOf(90, 90, 45, 110, 110, 45, 90, 90, 200),
            intArrayOf(90, 45, 45, 110, 110, 45, 45, 90, 200),
            intArrayOf(70, 45, 45, 110, 110, 45, 45, 70, 200),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 200)
    )),
    LIE(arrayOf(
            intArrayOf(110, 90, 90, 70, 70, 90, 90, 110, 500)
    )),
    SAY_HI(arrayOf(
            intArrayOf(70, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(170, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(170, 130, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(170, 50, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(170, 130, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(170, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(70, 90, 90, 90, 90, 90, 90, 90, 400)
    )),
    FIGHTING(arrayOf(
            intArrayOf(120, 90, 90, 110, 60, 90, 90, 70, 500),
            intArrayOf(120, 70, 70, 110, 60, 70, 70, 70, 500),
            intArrayOf(120, 110, 110, 110, 60, 110, 110, 70, 500),
            intArrayOf(120, 70, 70, 110, 60, 70, 70, 70, 500),
            intArrayOf(120, 110, 110, 110, 60, 110, 110, 70, 500),
            intArrayOf(70, 90, 90, 70, 110, 90, 90, 110, 500),
            intArrayOf(70, 70, 70, 70, 110, 70, 70, 110, 500),
            intArrayOf(70, 110, 110, 70, 110, 110, 110, 110, 500),
            intArrayOf(70, 70, 70, 70, 110, 70, 70, 110, 500),
            intArrayOf(70, 110, 110, 70, 110, 110, 110, 110, 500),
            intArrayOf(70, 90, 90, 70, 110, 90, 90, 110, 500)
    )),
    PUSH_UP(arrayOf(
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 500),
            intArrayOf(100, 90, 90, 80, 80, 90, 90, 100, 600),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 700),
            intArrayOf(100, 90, 90, 80, 80, 90, 90, 100, 800),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 900),
            intArrayOf(100, 90, 90, 80, 80, 90, 90, 100, 1500),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 2000),
            intArrayOf(135, 90, 90, 45, 45, 90, 90, 135, 200),
            intArrayOf(70, 90, 90, 45, 60, 90, 90, 135, 800),
            intArrayOf(70, 90, 90, 45, 110, 90, 90, 135, 800),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 800)
    )),
    SLEEP(arrayOf(
            intArrayOf(30, 90, 90, 150, 150, 90, 90, 30, 500),
            intArrayOf(30, 45, 135, 150, 150, 135, 45, 30, 500)
    )),
    DANCE_1(arrayOf(
            intArrayOf(90, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(50, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(90, 90, 90, 130, 90, 90, 90, 90, 400),
            intArrayOf(90, 90, 90, 90, 90, 90, 90, 50, 400),
            intArrayOf(90, 90, 90, 90, 130, 90, 90, 90, 400),
            intArrayOf(50, 90, 90, 90, 90, 90, 90, 90, 400),
            intArrayOf(90, 90, 90, 130, 90, 90, 90, 90, 400),
            intArrayOf(90, 90, 90, 90, 90, 90, 90, 50, 400),
            intArrayOf(90, 90, 90, 90, 130, 90, 90, 90, 400),
            intArrayOf(90, 90, 90, 90, 90, 90, 90, 90, 400)
    )),
    DANCE_2(arrayOf(
            intArrayOf(70, 45, 135, 110, 110, 135, 45, 70, 400),
            intArrayOf(115, 45, 135, 65, 110, 135, 45, 70, 400),
            intArrayOf(70, 45, 135, 110, 65, 135, 45, 115, 400),
            intArrayOf(115, 45, 135, 65, 110, 135, 45, 70, 400),
            intArrayOf(70, 45, 135, 110, 65, 135, 45, 115, 400),
            intArrayOf(115, 45, 135, 65, 110, 135, 45, 70, 400),
            intArrayOf(70, 45, 135, 110, 65, 135, 45, 115, 400),
            intArrayOf(115, 45, 135, 65, 110, 135, 45, 70, 400),
            intArrayOf(75, 45, 135, 105, 110, 135, 45, 70, 400)
    )),
    DANCE_3(arrayOf(
            intArrayOf(70, 45, 45, 110, 110, 135, 135, 70, 400),
            intArrayOf(110, 45, 45, 60, 70, 135, 135, 70, 400),
            intArrayOf(70, 45, 45, 110, 110, 135, 135, 70, 400),
            intArrayOf(110, 45, 45, 110, 70, 135, 135, 120, 400),
            intArrayOf(70, 45, 45, 110, 110, 135, 135, 70, 400),
            intArrayOf(110, 45, 45, 60, 70, 135, 135, 70, 400),
            intArrayOf(70, 45, 45, 110, 110, 135, 135, 70, 400),
            intArrayOf(110, 45, 45, 110, 70, 135, 135, 120, 400),
            intArrayOf(70, 45, 45, 110, 110, 135, 135, 70, 400),
            intArrayOf(70, 90, 90, 110, 110, 90, 90, 70, 400)
    ))
    ;

    val step: Int = servoProgramLine.size

}