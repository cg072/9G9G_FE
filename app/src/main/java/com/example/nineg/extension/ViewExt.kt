package com.example.nineg.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View?.showKeyboard() {
    this?.let { view ->
        view.requestFocus()
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
            view,
            InputMethodManager.SHOW_IMPLICIT
        )
    }
}

fun View?.hideKeyboard() {
    this?.let { view ->
        (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            view.windowToken,
            0
        )
    }
}