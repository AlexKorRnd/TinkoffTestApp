package com.example.core.base.extensions

import android.view.View

val DEFAULT_DELAY_MS = 500L

inline fun View.onClickDebounce(delayMs: Long = DEFAULT_DELAY_MS, crossinline l: (View?) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var notClicked = true
        override fun onClick(view: View) {
            if (notClicked) {
                notClicked = false
                l(view)
                view.postDelayed({ notClicked = true }, delayMs)
            }
        }
    })
}