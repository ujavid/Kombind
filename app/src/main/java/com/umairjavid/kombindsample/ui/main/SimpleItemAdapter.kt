package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.ui.KombindAdapter
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.model.SimpleItem

class SimpleItemAdapter(override val items: MutableLiveArrayList<Any>, override val handler: Any?) : KombindAdapter<KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) =
            if (items[position] is SimpleHeader) R.layout.item_simpleheader else R.layout.item_simpleitem

    interface ActionHandler {
        fun onSimpleItemClick(simpleItem: SimpleItem)

        fun onDeleteClick(simpleItemId: Int)
    }
}
