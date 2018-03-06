package com.umairjavid.kombindsample.repo

import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.model.SimpleItem

class SimpleItemRepository {
    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun getSimpleItems() = (0..10).map { if(it % 3 == 0) SimpleHeader("Header ${it / 3 + 1}") else SimpleItem(it, "Simple Item $it") }

    fun addItem(id: Int) = SimpleItem(id, "Simple Item $id")
}
