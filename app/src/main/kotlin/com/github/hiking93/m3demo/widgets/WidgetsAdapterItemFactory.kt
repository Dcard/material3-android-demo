package com.github.hiking93.m3demo.widgets

object WidgetsAdapterItemFactory {

    fun createItems(): List<WidgetsAdapterItem> = listOf(
        WidgetsAdapterItem.Buttons,
        WidgetsAdapterItem.Fab,
        WidgetsAdapterItem.SegmentedButtons,
        WidgetsAdapterItem.Chips,
        WidgetsAdapterItem.ProgressIndicators,
        WidgetsAdapterItem.Sliders,
        WidgetsAdapterItem.CheckBox,
        WidgetsAdapterItem.RadioButton,
        WidgetsAdapterItem.Switch,
        WidgetsAdapterItem.Tabs,
        WidgetsAdapterItem.TextFields,
        WidgetsAdapterItem.Cards,
    )
}