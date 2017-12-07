package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.databinding.ActivityMainBinding
import com.umairjavid.kombindsample.repo.ItemRepository

class MainActivity : KombindActivity<MainViewModel, MainViewModel.Factory, ActivityMainBinding>() {
    override val viewModelClass = MainViewModel::class.java
    override val layoutResId = R.layout.activity_main
    override val viewModelFactory = MainViewModel.Factory(application, ItemRepository())
}
