package com.github.hiking93.m3demo.popups.bottomsheet

sealed class DemoBottomSheetDialogAdapterItem {

    data class Option(
        val serialNumber: Int,
    ) : DemoBottomSheetDialogAdapterItem()
}