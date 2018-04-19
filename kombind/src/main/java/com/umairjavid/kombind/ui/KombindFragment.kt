package com.umairjavid.kombind.ui

import android.app.Fragment
import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umairjavid.kombind.BR
import com.umairjavid.kombind.ext.registerViewActionObserver

abstract class KombindFragment<VM: KombindViewModel> : Fragment(), LifecycleOwner, ViewModelStoreOwner {
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutResId: Int
    protected lateinit var viewBinding: ViewDataBinding
    lateinit var viewModel: VM

    internal var lifecycleRegistry = LifecycleRegistry(this)
    internal var viewModelStore: ViewModelStore? = null

    override fun getLifecycle() = lifecycleRegistry

    override fun getViewModelStore(): ViewModelStore {
        if (activity == null) throw IllegalStateException("Can't access ViewModels from detached fragment")
        if (viewModelStore == null) viewModelStore = ViewModelStore()
        return viewModelStore!!
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProvider(getViewModelStore(), provideViewModelFactory()).get(viewModelClass)
        viewModel.activityViewModel = (activity as KombindActivity<*>).viewModel
        viewBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewBinding.setVariable(BR.viewModel, viewModel)
        viewBinding.setLifecycleOwner(this)
        registerViewActionObserver(viewModel.viewAction)
        onViewLoad(savedInstanceState)
        return viewBinding.root
    }

    abstract fun provideViewModelFactory(): ViewModelProvider.Factory

    open fun onBeforeViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }

    open fun onViewLoad(savedInstanceState: Bundle?) {
        //Intentionally empty so that subclasses can override if necessary
    }

    override fun onStart() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        super.onResume()
    }

    override fun onPause() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        super.onStop()
    }

    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        if (viewModelStore != null) viewModelStore!!.clear()
        super.onDestroy()
    }
}
