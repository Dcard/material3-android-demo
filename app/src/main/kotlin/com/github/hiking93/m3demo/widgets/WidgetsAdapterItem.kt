package com.github.hiking93.m3demo.widgets

sealed class WidgetsAdapterItem {
    object Buttons : WidgetsAdapterItem()
    object Fab : WidgetsAdapterItem()
    object SegmentedButtons : WidgetsAdapterItem()
    object Chips : WidgetsAdapterItem()
    object ProgressIndicators : WidgetsAdapterItem()
    object Sliders : WidgetsAdapterItem()
    object CheckBox : WidgetsAdapterItem()
    object RadioButton : WidgetsAdapterItem()
    object Switch : WidgetsAdapterItem()
    object Tabs : WidgetsAdapterItem()
    object TextFields : WidgetsAdapterItem()
    object Cards : WidgetsAdapterItem()
}