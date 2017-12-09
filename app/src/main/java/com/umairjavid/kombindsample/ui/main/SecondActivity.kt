package com.umairjavid.kombindsample.ui.main

import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombindsample.R

class SecondActivity(override val layoutResId : Int =  R.layout.activity_second) : KombindActivity<SecondViewModel>() {
    override fun provideViewModelFactory() = SecondViewModel.Factory(application)
    override val viewModelClass = SecondViewModel::class.java
}
