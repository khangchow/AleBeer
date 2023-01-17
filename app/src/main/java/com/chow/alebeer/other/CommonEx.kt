package com.chow.alebeer.other

import android.view.View

fun Int?.orZero() = this ?: 0

fun View.show() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

