package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.anontation.SimpleKombindAdapter
import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.model.MutableLiveField

class MainState {
    val title = MutableLiveField("Items Loaded!")
    @SimpleKombindAdapter
    val items = MutableLiveArrayList<Any>()

    operator fun invoke(func: MainState.() -> Unit) = func()
}
