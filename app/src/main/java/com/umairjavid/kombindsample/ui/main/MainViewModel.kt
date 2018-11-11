package com.umairjavid.kombindsample.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.umairjavid.kombind.ui.KombindViewModel
import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.model.SimpleItem
import com.umairjavid.kombindsample.repo.SimpleItemRepository

class MainViewModel(application: Application, private val simpleItemRepository: SimpleItemRepository) : KombindViewModel(application),
        SimpleItemAdapter.HeaderActionHandler,
        SimpleItemAdapter.ItemActionHandler {
    val state = MainState()

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
