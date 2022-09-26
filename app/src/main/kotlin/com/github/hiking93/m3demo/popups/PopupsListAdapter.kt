package com.github.hiking93.m3demo.popups

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PopupsListAdapter(
    private val interaction: Interaction,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_POPUP_OPTION = 0
    }

    interface Interaction {
        fun onPopupOptionClick(option: PopupsListAdapterItem.PopupOption)
    }

    private val items: List<PopupsListAdapterItem> = PopupsListAdapterItemFactory.createItems()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is PopupsListAdapterItem.PopupOption -> TYPE_POPUP_OPTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_POPUP_OPTION -> PopupOptionViewHolder.create(parent).apply {
            itemView.setOnClickListener {
                adapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
                    val item = items[position] as PopupsListAdapterItem.PopupOption
                    interaction.onPopupOptionClick(item)
                }
            }
        }
        else -> error("Unknown view holder for type \$viewType.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is PopupsListAdapterItem.PopupOption -> {
                holder as PopupOptionViewHolder
                holder.bind(
                    text = holder.itemView.context.getText(item.textResId),
                )
            }
        }
    }
}