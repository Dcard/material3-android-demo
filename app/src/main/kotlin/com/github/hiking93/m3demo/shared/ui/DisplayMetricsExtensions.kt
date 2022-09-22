package com.github.hiking93.m3demo.shared.ui

import android.content.Context
import kotlin.math.roundToInt

fun Float.dpToPx(context: Context) = this * context.resources.displayMetrics.density

fun Float.dpToPxSize(context: Context) = dpToPx(context).roundToInt()