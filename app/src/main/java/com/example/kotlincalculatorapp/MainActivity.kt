package com.example.kotlincalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numAction(view: View) {}
    fun operatorsAction(view: View) {}
    fun backspaceAction(view: View) {}
    fun allClearAction(view: View) {}
    fun equalsAction(view: View) {}
}