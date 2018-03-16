package com.umairjavid.kombind.ui

import android.app.Dialog
import android.app.DialogFragment
import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.android.databinding.library.baseAdapters.BR
import com.umairjavid.kombind.ext.registerViewActionObserver

abstract class KombindDialogFragment<VM: KombindViewModel> : DialogFragment(), LifecycleOwner, ViewModelStoreOwner {
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setStyle(STYLE_NO_FRAME, 0)
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProvider(getViewModelStore(), provideViewModelFactory()).get(viewModelClass)
        viewModel.activityViewModel = (activity as KombindActivity<*>).viewModel
        viewBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        viewBinding.setVariable(BR.viewModel, viewModel)
        registerViewActionObserver(viewModel.viewAction)
        onViewLoad(savedInstanceState)
        return viewBinding.root
    }

    abstract fun provideViewModelFactory(): ViewModelProvider.Factory

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        val width = resources.displayMetrics.widthPixels
        dialog.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

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
