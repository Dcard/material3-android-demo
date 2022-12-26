package com.github.hiking93.m3demo.popups.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hiking93.m3demo.databinding.ListItemBottomSheetDialogOptionBinding

class DemoBottomSheetDialogOptionViewHolder(
    private val binding: ListItemBottomSheetDialogOptionBinding,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {

        fun create(parent: ViewGroup) = DemoBottomSheetDialogOptionViewHolder(
            ListItemBottomSheetDialogOptionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false,
            )
        )
    }

    fun bind(text: CharSequence) {
        binding.titleTextView.text = text
    }
}