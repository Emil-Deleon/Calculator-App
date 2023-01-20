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


}