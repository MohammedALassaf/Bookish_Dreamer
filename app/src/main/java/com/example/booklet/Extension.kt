package com.example.booklet

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.showKeyboard(activity: Activity) {
    this.post {
        this.requestFocus()
        val runnable = Runnable {
            val keyboard =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(this, 0)
        }
        this.postDelayed(runnable, 50)
    }
}