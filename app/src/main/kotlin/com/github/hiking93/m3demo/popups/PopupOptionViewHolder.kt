package com.github.hiking93.m3demo.popups

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hiking93.m3demo.databinding.ListItemPopupOptionBinding

class PopupOptionViewHolder(
    private val binding: ListItemPopupOptionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) = PopupOptionViewHolder(
            ListItemPopupOptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false,
            )
        )
    }

    fun bind(text: CharSequence) {
        binding.titleTextView.text = text
    }
}