package com.github.hiking93.m3demo.shared

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP
import androidx.core.view.WindowInsetsAnimationCompat.Callback.DispatchMode
import androidx.core.view.WindowInsetsCompat

fun View.doOnWindowInsetsChanged(
    listenToAnimation: Boolean = false,
    @DispatchMode dispatchMode: Int = DISPATCH_MODE_STOP,
    callback: (v: View, insets: WindowInsetsCompat) -> WindowInsetsCompat,
) {
    var isAnimationRunning = false
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        if (isAnimationRunning) insets else callback(v, insets)
    }
    if (listenToAnimation) {
        ViewCompat.setWindowInsetsAnimationCallback(
            this,
            object : WindowInsetsAnimationCompat.Callback(dispatchMode) {

                override fun onPrepare(animation: WindowInsetsAnimationCompat) {
                    super.onPrepare(animation)
                    isAnimationRunning = true
                }

                override fun onProgress(
                    insets: WindowInsetsCompat,
                    runningAnimations: MutableList<WindowInsetsAnimationCompat>,
                ) = callback(this@doOnWindowInsetsChanged, insets)

                override fun onEnd(animation: WindowInsetsAnimationCompat) {
                    super.onEnd(animation)
                    isAnimationRunning = false
                }
            }
        )
    }
}