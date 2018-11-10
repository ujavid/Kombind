package com.umairjavid.kombindsample.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombindsample.R
import com.umairjavid.kombindsample.repo.SimpleItemRepository
import kotlinx.android.synthetic.main.activity_main.*

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
            adapter = SimpleItemAdapter(viewModel.state.items, viewModel)
                    .registerObserver(this@MainActivity)
        }
    }
}
