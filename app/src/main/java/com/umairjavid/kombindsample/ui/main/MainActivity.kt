package com.umairjavid.kombindsample.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.model.SimpleHeader
import com.umairjavid.kombindsample.repo.SimpleItemRepository
import kotlinx.android.synthetic.main.activity_main.simple_item_list
import main.Kombind_items_Adapter


class MainActivity : KombindActivity<MainViewModel>() {
    override val viewModelClass = MainViewModel::class.java
    override val layoutResId = R.layout.activity_main

    //Setup dependencies/dependency injection framework to load any dependencies here
    override fun provideViewModelFactory() = MainViewModel.Factory(application, SimpleItemRepository())

    override fun onViewLoad(savedInstanceState: Bundle?) {
        setupSimpleItemList()
    }

    private fun setupSimpleItemList() {
        simple_item_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = object : Kombind_items_Adapter(viewModel.state.items, viewModel) {
                override fun getLayout(position: Int) = when(items[position]) {
                    is SimpleHeader -> R.layout.item_simpleheader
                    else -> R.layout.item_simpleitem
                }
            } .registerObserver(this@MainActivity)
        }
    }
}
