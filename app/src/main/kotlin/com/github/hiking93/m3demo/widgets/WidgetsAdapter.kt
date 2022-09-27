package com.github.hiking93.m3demo.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.github.hiking93.m3demo.R
import com.github.hiking93.m3demo.databinding.*

class WidgetsAdapter : RecyclerView.Adapter<WidgetSectionViewHolder<*>>() {

    companion object {
        private const val TYPE_BUTTONS = 0
        private const val TYPE_FAB = 10
        private const val TYPE_SEGMENTED_BUTTONS = 20
        private const val TYPE_CHIPS = 30
        private const val TYPE_PROGRESS_INDICATORS = 40
        private const val TYPE_SLIDERS = 50
        private const val TYPE_CHECK_BOX = 60
        private const val TYPE_RADIO_BUTTON = 70
        private const val TYPE_SWITCH = 80
        private const val TYPE_TABS = 90
        private const val TYPE_TEXT_FIELDS = 100
        private const val TYPE_CARDS = 110
    }

    private val items: List<WidgetsAdapterItem> = WidgetsAdapterItemFactory.createItems()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is WidgetsAdapterItem.Buttons -> TYPE_BUTTONS
        is WidgetsAdapterItem.Fab -> TYPE_FAB
        is WidgetsAdapterItem.SegmentedButtons -> TYPE_SEGMENTED_BUTTONS
        is WidgetsAdapterItem.Chips -> TYPE_CHIPS
        is WidgetsAdapterItem.ProgressIndicators -> TYPE_PROGRESS_INDICATORS
        is WidgetsAdapterItem.Sliders -> TYPE_SLIDERS
        is WidgetsAdapterItem.CheckBox -> TYPE_CHECK_BOX
        is WidgetsAdapterItem.RadioButton -> TYPE_RADIO_BUTTON
        is WidgetsAdapterItem.Switch -> TYPE_SWITCH
        is WidgetsAdapterItem.Tabs -> TYPE_TABS
        is WidgetsAdapterItem.TextFields -> TYPE_TEXT_FIELDS
        is WidgetsAdapterItem.Cards -> TYPE_CARDS
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = WidgetSectionViewHolder(
        binding = createViewBinding(
            parent = parent,
            viewType = viewType,
        ),
    )

    override fun onBindViewHolder(holder: WidgetSectionViewHolder<*>, position: Int) {
        val context = holder.itemView.context
        when (val item = items[position]) {
            is WidgetsAdapterItem.TextFields -> {
                val binding = holder.binding as ListItemWidgetTextFieldsBinding
                sequenceOf(
                    binding.filledErrorTextInputLayout,
                    binding.outlinedErrorTextInputLayout,
                    binding.outlinedErrorDenseTextInputLayout,
                ).forEach {
                    it.error = context.getText(R.string.widgets_text_field_error_description)
                }
            }
            else -> {}
        }
    }

    private fun createViewBinding(parent: ViewGroup, viewType: Int): ViewBinding {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_BUTTONS -> {
                ListItemWidgetButtonsBinding.inflate(inflater, parent, false)
            }
            TYPE_FAB -> {
                ListItemWidgetFabBinding.inflate(inflater, parent, false)
            }
            TYPE_SEGMENTED_BUTTONS -> {
                ListItemWidgetSegmentedButtonsBinding.inflate(inflater, parent, false)
            }
            TYPE_CHIPS -> {
                ListItemWidgetChipsBinding.inflate(inflater, parent, false)
            }
            TYPE_PROGRESS_INDICATORS -> {
                ListItemWidgetProgressIndicatorsBinding.inflate(inflater, parent, false)
            }
            TYPE_SLIDERS -> {
                ListItemWidgetSlidersBinding.inflate(inflater, parent, false)
            }
            TYPE_CHECK_BOX -> {
                ListItemWidgetCheckboxBinding.inflate(inflater, parent, false)
            }
            TYPE_RADIO_BUTTON -> {
                ListItemWidgetRadioButtonBinding.inflate(inflater, parent, false)
            }
            TYPE_SWITCH -> {
                ListItemWidgetSwitchBinding.inflate(inflater, parent, false)
            }
            TYPE_TABS -> {
                ListItemWidgetTabsBinding.inflate(inflater, parent, false)
            }
            TYPE_TEXT_FIELDS -> {
                ListItemWidgetTextFieldsBinding.inflate(inflater, parent, false)
            }
            TYPE_CARDS -> {
                ListItemWidgetCardsBinding.inflate(inflater, parent, false)
            }
            else -> error("View binding is undefined for view type $viewType.")
        }
    }
}