package com.example.foodium.utils

import android.view.View

object ExtensionFunctions {

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.INVISIBLE
    }

}