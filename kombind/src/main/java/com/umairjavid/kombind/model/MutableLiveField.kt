package com.umairjavid.kombind.model

import android.arch.lifecycle.MutableLiveData

open class MutableLiveField<T>(default: T? = null) : MutableLiveData<T>() {
    init {
        if (default != null) value = default
    }
}
