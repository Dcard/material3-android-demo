package com.github.hiking93.m3demo.popups

import com.github.hiking93.m3demo.R
import com.github.hiking93.m3demo.popups.PopupsAdapterItem.PopupOption
import com.github.hiking93.m3demo.popups.PopupsAdapterItem.PopupOption.Type

object PopupsAdapterItemFactory {

    fun createItems(): List<PopupsAdapterItem> = listOf(
        PopupOption(
            type = Type.BasicDialog,
            textResId = R.string.popups_item_dialog_basic_dialog_option,
        ),
        PopupOption(
            type = Type.BasicDialogWithButtons,
            textResId = R.string.popups_item_dialog_basic_dialog_with_buttons_option,
        ),
        PopupOption(
            type = Type.Snackbar,
            textResId = R.string.popups_item_snackbar_option,
        ),
        PopupOption(
            type = Type.SnackbarWithAction,
            textResId = R.string.popups_item_snackbar_with_action_option,
        ),
        PopupOption(
            type = Type.DatePicker,
            textResId = R.string.popups_item_date_picker_option,
        ),
        PopupOption(
            type = Type.DateRangePicker,
            textResId = R.string.popups_item_date_range_picker_option,
        ),
        PopupOption(
            type = Type.TimePicker,
            textResId = R.string.popups_item_time_picker_option,
        ),
    )
}