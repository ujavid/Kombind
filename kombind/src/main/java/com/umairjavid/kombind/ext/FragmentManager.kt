package com.umairjavid.kombind.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.dismiss(tag: String) =
        (this.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
