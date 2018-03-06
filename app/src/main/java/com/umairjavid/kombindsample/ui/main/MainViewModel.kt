package com.umairjavid.kombindsample.ui.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.databinding.ObservableArrayList
import com.umairjavid.kombind.ui.KombindViewModel
import com.umairjavid.kombindsample.model.SimpleItem
import com.umairjavid.kombindsample.repo.SimpleItemRepository

class MainViewModel(application: Application, private val simpleItemRepository: SimpleItemRepository) : KombindViewModel(application), SimpleItemAdapter.ActionHandler {
    val items = ObservableArrayList<Any>()

    init {
        loadItems()
    }

    private fun loadItems() = items.addAll(simpleItemRepository.getSimpleItems())

    fun onFABClick() = items.add(simpleItemRepository.addItem(items.size + 1))

    override fun onSimpleItemClick(simpleItem: SimpleItem) {
        //TODO open edit dialog
    }

    override fun onDeleteClick(simpleItemId: Int) {
        items.remove(items.find { it is SimpleItem && it.id == simpleItemId })
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
