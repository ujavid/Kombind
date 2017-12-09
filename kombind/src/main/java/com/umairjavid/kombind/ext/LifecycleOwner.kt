package com.umairjavid.kombind.ext

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.app.DialogFragment
import android.app.Fragment
import android.support.v4.app.FragmentActivity
import com.umairjavid.kombind.model.ViewAction

import java.util.*

fun LifecycleOwner.registerViewActionObserver(viewActionQueue: MutableLiveData<Queue<ViewAction>>) {
    viewActionQueue.observe(this, Observer<Queue<ViewAction>> {
        while(it?.isNotEmpty() == true) {
            val viewAction = it.remove()
            when (viewAction) {
               is ViewAction.StartActivity -> when {
                    this is Activity -> startActivity(viewAction.intent)
                    this is Fragment -> startActivity(viewAction.intent)
                    this is android.support.v4.app.Fragment -> startActivity(viewAction.intent)
                }
                is ViewAction.StartActivityForResult -> when {
                    this is Activity -> startActivityForResult(viewAction.intent, viewAction.requestCode)
                    this is Fragment -> startActivityForResult(viewAction.intent, viewAction.requestCode)
                    this is android.support.v4.app.Fragment -> startActivityForResult(viewAction.intent, viewAction.requestCode)
                }
                is ViewAction.ShowDialog -> when {
                    this is Activity -> viewAction.builder.show(fragmentManager)
                    this is Fragment -> viewAction.builder.show(childFragmentManager)
                }
                is ViewAction.ShowV4Dialog -> when {
                    this is FragmentActivity -> viewAction.builder.show(supportFragmentManager)
                    this is android.support.v4.app.Fragment -> viewAction.builder.show(childFragmentManager)
                }
                is ViewAction.DismissDialog -> when {
                    this is DialogFragment -> dismiss()
                    this is android.support.v4.app.DialogFragment -> dismiss()
                    this is Activity -> fragmentManager.dismiss(viewAction.tag)
                    this is FragmentActivity -> supportFragmentManager.dismiss(viewAction.tag)
                    this is Fragment -> childFragmentManager.dismiss(viewAction.tag)
                    this is android.support.v4.app.Fragment -> childFragmentManager.dismiss(viewAction.tag)
                }
                ViewAction.Finish -> when {
                    this is Activity -> finish()
                    this is Fragment -> activity.finish()
                    this is android.support.v4.app.Fragment -> activity?.finish()
                }
            }
        }
    })
}
