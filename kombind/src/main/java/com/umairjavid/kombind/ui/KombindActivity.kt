package com.umairjavid.kombind.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umairjavid.kombind.BR
import com.umairjavid.kombind.ext.registerViewActionObserver

abstract class KombindActivity<VM: KombindViewModel> : AppCompatActivity() {
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutResId: Int
    protected lateinit var viewBinding: ViewDataBinding
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProviders.of(this, provideViewModelFactory()).get(viewModelClass)
        viewBinding = DataBindingUtil.setContentView(this, layoutResId)
        viewBinding.setVariable(BR.viewModel, viewModel)
        viewBinding.setLifecycleOwner(this)
        registerViewActionObserver(viewModel.viewAction)
        onViewLoad(savedInstanceState)
    }

    abstract fun provideViewModelFactory(): ViewModelProvider.Factory

    open fun onBeforeViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }

    open fun onViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }
}
