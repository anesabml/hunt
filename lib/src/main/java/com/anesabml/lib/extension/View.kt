package com.anesabml.lib.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(resString: Int, length: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, resString, length).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}
