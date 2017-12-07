package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.repo.ItemRepository

class MainActivity : KombindActivity<MainViewModel>() {
    override val viewModelClass = MainViewModel::class.java
    override val layoutResId = R.layout.activity_main

    //Setup dependencies/dependency injection framework to load any dependencies here
    override fun provideViewModelFactory() = MainViewModel.Factory(application, ItemRepository())
}
