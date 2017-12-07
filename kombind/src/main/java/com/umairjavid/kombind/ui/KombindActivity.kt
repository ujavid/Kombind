package com.umairjavid.kombind.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.umairjavid.kombind.ext.registerViewActionObserver

abstract class KombindActivity<VM: KombindViewModel, VMF: ViewModelProvider.Factory, VDB: ViewDataBinding> : AppCompatActivity() {
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutResId: Int
    protected abstract val viewModelVariableId: Int
    protected lateinit var viewModelFactory: VMF
    protected lateinit var viewBinding: VDB
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        viewBinding = DataBindingUtil.setContentView(this, layoutResId)
        viewBinding.setVariable(viewModelVariableId, viewModel)
        registerViewActionObserver(viewModel.viewAction)
        onViewLoad(savedInstanceState)
    }

    open fun onBeforeViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }

    open fun onViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }
}
