package com.umairjavid.kombind.ext

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.umairjavid.kombind.model.ViewAction
import java.util.Queue

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
                    this is AppCompatActivity -> viewAction.builder.show(supportFragmentManager)
                    this is Fragment -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) viewAction.builder.show(childFragmentManager)
                }
                is ViewAction.ShowV4Dialog -> when {
                    this is FragmentActivity -> viewAction.builder.show(supportFragmentManager)
                    this is Fragment -> viewAction.builder.show(childFragmentManager)
                }
                is ViewAction.DismissDialog -> when {
                    this is DialogFragment -> dismiss()
                    this is AppCompatActivity -> supportFragmentManager.dismiss(viewAction.tag)
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
