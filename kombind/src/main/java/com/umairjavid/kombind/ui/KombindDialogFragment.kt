package com.umairjavid.kombind.ui

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.library.baseAdapters.BR
import com.umairjavid.kombind.ext.registerViewActionObserver

abstract class KombindDialogFragment<VM: KombindViewModel> : DialogFragment() {
    protected abstract val viewModelClass: Class<VM>
    protected abstract val layoutResId: Int
    protected lateinit var viewBinding: ViewDataBinding
    lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setStyle(STYLE_NO_FRAME, 0)
        onBeforeViewLoad(savedInstanceState)
        viewModel = ViewModelProviders.of(this, provideViewModelFactory()).get(viewModelClass)
        if (activity is KombindActivity<*>) {
            viewModel.activityViewModel = (activity as KombindActivity<*>).viewModel
        }
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
}
