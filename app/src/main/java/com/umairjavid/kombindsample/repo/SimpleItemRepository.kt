package com.umairjavid.kombindsample.repo

import com.umairjavid.kombindsample.model.SimpleItem

class SimpleItemRepository {
    fun getSimpleItems() = (1..5).map { SimpleItem(it, "Simple Item $it") }

    fun addItem(id: Int) = SimpleItem(id, "Simple Item $id")
}
