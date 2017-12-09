package com.umairjavid.kombindsample.ui.main

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.umairjavid.kombind.ui.KombindViewModel

class SecondViewModel(application: Application): KombindViewModel(application) {
   val secondActivityText = "This is the second activity"
    class Factory(
            private val application: Application
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>) = SecondViewModel(
                application
        ) as T
    }
}
