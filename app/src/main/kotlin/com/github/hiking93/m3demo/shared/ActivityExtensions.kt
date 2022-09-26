package com.github.hiking93.m3demo.shared

import android.app.Activity
import androidx.core.view.WindowCompat

fun Activity.applyEdgeToEdge() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}