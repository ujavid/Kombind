package com.umairjavid.kombindsample.ui.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.databinding.ObservableField
import com.umairjavid.kombind.model.ViewAction
import com.umairjavid.kombind.ui.KombindViewModel
import com.umairjavid.kombindsample.repo.ItemRepository

class MainViewModel(application: Application, private val itemRepository: ItemRepository) : KombindViewModel(application) {
    val text = ObservableField<String>("Hello Kombind!")
    val buttonText = "Switch to second activity"
    val switchActivity = ViewAction.StartActivity(Intent(application, SecondActivity::class.java))

    class Factory(
            private val application: Application,
            private val itemRepository: ItemRepository
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(
                application,
                itemRepository
        ) as T
    }
}
