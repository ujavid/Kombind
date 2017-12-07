package com.umairjavid.kombind.ext

import android.app.DialogFragment
import android.app.FragmentManager

fun FragmentManager.dismiss(tag: String) =
        (this.findFragmentByTag(tag) as? DialogFragment)?.dismiss()

fun android.support.v4.app.FragmentManager.dismiss(tag: String) =
        (this.findFragmentByTag(tag) as? android.support.v4.app.DialogFragment)?.dismiss()
