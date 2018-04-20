package com.umairjavid.kombind.ui.v4

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umairjavid.kombind.BR
import com.umairjavid.kombind.ext.registerViewActionObserver
import com.umairjavid.kombind.ui.KombindActivity
import com.umairjavid.kombind.ui.KombindViewModel

abstract class KombindFragment<VM: KombindViewModel> : Fragment() {
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutResId: Int
    protected lateinit var viewBinding: ViewDataBinding
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProviders.of(this, provideViewModelFactory()).get(viewModelClass)
        viewModel.activityViewModel = (activity as KombindActivity<*>).viewModel
        viewBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewBinding.setVariable(BR.viewModel, viewModel)
        viewBinding.setLifecycleOwner(this)
        registerViewActionObserver(viewModel.viewAction)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
