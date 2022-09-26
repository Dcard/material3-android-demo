package com.github.hiking93.m3demo.shared

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.putArguments(args: Bundle) {
    arguments = (arguments ?: Bundle()).apply {
        putAll(args)
    }
}