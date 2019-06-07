package com.umairjavid.kombind.ext

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.umairjavid.kombind.model.ViewAction
import java.util.*

fun LifecycleOwner.registerViewActionObserver(viewActionQueue: MutableLiveData<Queue<ViewAction>>) {
    viewActionQueue.observe(this, Observer<Queue<ViewAction>> {
        while(it?.isNotEmpty() == true) {
            when (val viewAction = it.remove()) {
                is ViewAction.StartActivity -> when {
                    this is Activity -> startActivity(viewAction.intent)
                    this is Fragment -> startActivity(viewAction.intent)
                }
                is ViewAction.StartActivityForResult -> when {
                    this is Activity -> startActivityForResult(viewAction.intent, viewAction.requestCode)
                    this is Fragment -> startActivityForResult(viewAction.intent, viewAction.requestCode)
                }
                is ViewAction.ShowDialog -> when {
                    this is FragmentActivity -> viewAction.builder.show(supportFragmentManager)
                    this is Fragment -> viewAction.builder.show(childFragmentManager)
                }
                is ViewAction.DismissDialog -> when {
                    this is DialogFragment -> dismiss()
                    this is FragmentActivity -> supportFragmentManager.dismiss(viewAction.tag)
                    this is Fragment -> childFragmentManager.dismiss(viewAction.tag)
                }
                ViewAction.Finish -> when {
                    this is Activity -> finish()
                    this is Fragment -> activity?.finish()
                }
            }
        }
    })
}
