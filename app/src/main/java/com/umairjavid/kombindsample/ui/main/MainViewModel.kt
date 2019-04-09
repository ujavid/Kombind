package com.umairjavid.kombindsample.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umairjavid.kombind.anontation.SimpleKombindAdapter
import com.umairjavid.kombind.model.MutableLiveArrayList
import com.umairjavid.kombind.ui.KombindViewModel
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.model.SimpleItem
import com.umairjavid.kombindsample.repo.SimpleItemRepository
import kotlinx.android.synthetic.main.activity_main.view.simple_item_list

class MainViewModel(application: Application, private val simpleItemRepository: SimpleItemRepository) : KombindViewModel(application),
        SimpleItemAdapter.HeaderActionHandler,
        SimpleItemAdapter.ItemActionHandler {
   val state = MainState()
   @SimpleKombindAdapter(R.id.simple_item_list) val data: MutableLiveArrayList<String> = MutableLiveArrayList(mutableListOf())
    init {
        loadItems()
    }

    private fun loadItems() = state { items.addAll(simpleItemRepository.getSimpleItems()) }

    fun onFABClick() = state {
        val item = simpleItemRepository.addItem(items.size + 1)
        items.add(item)
        title.value = "${item.name} Added!"
    }

    override fun onHeaderItemClick(headerItem: SimpleHeader) {
        state { title.value = "${headerItem.title} Clicked!" }
    }

    override fun onSimpleItemClick(simpleItem: SimpleItem) {
        state { title.value = "${simpleItem.name} Clicked!" }
    }

    override fun onDeleteClick(simpleItemId: Int) {
        state {
            val item = items.find { it is SimpleItem && it.id == simpleItemId } as SimpleItem
            items.remove(item)
            title.value = "${item.name} Deleted!"
        }
    }

    class Factory(
            private val application: Application,
            private val simpleItemRepository: SimpleItemRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(
                application,
                simpleItemRepository
        ) as T
    }
}
