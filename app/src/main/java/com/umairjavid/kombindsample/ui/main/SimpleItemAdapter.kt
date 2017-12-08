package com.umairjavid.kombindsample.ui.main

import android.databinding.ObservableArrayList
import com.umairjavid.kombind.ui.KombindAdapter
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.model.SimpleItem

class SimpleItemAdapter(items: ObservableArrayList<SimpleItem>, override val handler: Any?) : KombindAdapter<SimpleItem, KombindAdapter.ViewHolder>(items) {
    override fun getLayout(position: Int) = R.layout.item_simpleitem

    interface ActionHandler {
        fun onSimpleItemClick(simpleItem: SimpleItem)

        fun onDeleteClick(simpleItemId: Int)
    }
}
