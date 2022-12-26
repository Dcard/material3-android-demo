package com.github.hiking93.m3demo.popups.bottomsheet

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hiking93.m3demo.R

class DemoBottomSheetDialogAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_OPTION = 0
        private const val OPTION_LIST_SIZE = 50
    }

    interface Interaction {
        fun onOptionClick(option: DemoBottomSheetDialogAdapterItem.Option)
    }

    private val items: List<DemoBottomSheetDialogAdapterItem> = (1..OPTION_LIST_SIZE).map {
        DemoBottomSheetDialogAdapterItem.Option(
            serialNumber = it,
        )
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is DemoBottomSheetDialogAdapterItem.Option -> TYPE_OPTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_OPTION -> DemoBottomSheetDialogOptionViewHolder.create(parent).apply {
            itemView.setOnClickListener {
                adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
                    val item = items[position] as DemoBottomSheetDialogAdapterItem.Option
                    interaction.onOptionClick(item)
                }
            }
        }
        else -> error("Unknown view holder for type \$viewType.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is DemoBottomSheetDialogAdapterItem.Option -> {
                holder as DemoBottomSheetDialogOptionViewHolder
                holder.bind(
                    text = holder.itemView.context.getString(
                        R.string.popups_dialog_bottom_sheet_option_format,
                        item.serialNumber,
                    ),
                )
            }
        }
    }
}