package com.umairjavid.kombind.model

import androidx.lifecycle.MutableLiveData

open class MutableLiveField<T>(default: T? = null) : MutableLiveData<T>() {
    init {
        if (default != null) value = default
    }
}
