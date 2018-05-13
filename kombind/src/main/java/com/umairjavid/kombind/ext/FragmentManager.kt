package com.umairjavid.kombind.ext

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

fun FragmentManager.dismiss(tag: String) =
        (this.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
