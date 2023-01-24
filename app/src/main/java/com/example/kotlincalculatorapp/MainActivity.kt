package com.example.kotlincalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{


    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal) {
                    workingView.append(view.text)
                    canAddDecimal = false
                }
            }
            else {
                workingView.append(view.text)
            }
            canAddOperation = true
        }
    }

    fun operatorsAction(view: View) {
        if (view is Button && canAddOperation) {
            workingView.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    fun backspaceAction(view: View) {
        val length = workingView.length()
        if (length > 0) {
            workingView.text = workingView.text.subSequence(0, length - 1)
        }
    }

    fun allClearAction(view: View) {
        workingView.text = ""
        resultView.text = ""
    }

    fun equalsAction(view: View) {
        resultView.text = calculateResults()
    }

    private fun calculateResults(): String {
       val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) {
            return ""
        }

        val multiplyDivide = multiplyDivideCalculate(digitsOperators)
        if (multiplyDivide.isEmpty()) {
            return ""
        }

        val result = addSubtractCalculate(multiplyDivide)
        return result.toString()
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '－')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun multiplyDivideCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('×') || list.contains('÷')) {
            list = calcMultiplyDivide(list)
        }
        return list
    }

    private fun calcMultiplyDivide(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()

        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val previousDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float

                when (operator) {
                    '×' -> {
                        newList.add(previousDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '÷' -> {
                        newList.add(previousDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(previousDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex) {
                newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in workingView.text) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }

        if (currentDigit != "") {
            list.add(currentDigit.toFloat())
        }

        return list
    }


}