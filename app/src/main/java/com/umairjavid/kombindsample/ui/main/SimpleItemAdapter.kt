package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.ui.KombindAdapter
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.model.SimpleItem

class SimpleItemAdapter(items: MutableLiveArrayList<Any>, private val handler: Any) : KombindAdapter<KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = when(items[position]) {
        is SimpleHeader -> R.layout.item_simpleheader
        else -> R.layout.item_simpleitem
    }

    override fun getHandler(position: Int) = handler

    interface HeaderActionHandler {
        fun onHeaderItemClick(headerItem: SimpleHeader)
    }

    interface ItemActionHandler {
        fun onSimpleItemClick(simpleItem: SimpleItem)

        fun onDeleteClick(simpleItemId: Int)
    }
}
