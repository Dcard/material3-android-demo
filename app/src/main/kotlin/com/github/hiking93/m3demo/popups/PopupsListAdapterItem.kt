package com.github.hiking93.m3demo.popups

import androidx.annotation.StringRes

sealed class PopupsListAdapterItem {

    data class PopupOption(
        val type: Type,
        @StringRes val textResId: Int,
    ) : PopupsListAdapterItem() {

        enum class Type {
            BasicDialog,
            BasicDialogWithButtons,
            Snackbar,
            SnackbarWithAction,
        }
    }
}