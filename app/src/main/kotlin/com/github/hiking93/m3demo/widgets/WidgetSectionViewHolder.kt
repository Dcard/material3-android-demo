package com.github.hiking93.m3demo.widgets

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class WidgetSectionViewHolder<T : ViewBinding>(
    val binding: T,
) : RecyclerView.ViewHolder(binding.root)