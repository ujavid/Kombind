package com.umairjavid.kombind.model

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.umairjavid.kombind.model.AdapterAction.NotifyDataSetChanged
import com.umairjavid.kombind.model.AdapterAction.NotifyItemRangeChanged
import com.umairjavid.kombind.model.AdapterAction.NotifyItemRangeInserted
import com.umairjavid.kombind.model.AdapterAction.NotifyItemRangeRemoved
import java.util.Queue
import java.util.concurrent.LinkedBlockingQueue

open class MutableLiveArrayList<T: Any>(default: Collection<T>? = null) : ObservableArrayList<T>() {
    private val adapterActionQueue = LinkedBlockingQueue<AdapterAction>()
    private val adapterAction = MutableLiveData<Queue<AdapterAction>>()

    init {
        if (default != null)  addAll(default)

        addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<T>>() {
            override fun onItemRangeRemoved(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = addAdapterAction(NotifyItemRangeRemoved(positionStart, itemCount))
            override fun onChanged(sender: ObservableArrayList<T>?) = addAdapterAction(NotifyDataSetChanged)
            override fun onItemRangeMoved(sender: ObservableArrayList<T>?, positionStart: Int, toPosition: Int, itemCount: Int) = addAdapterAction(NotifyItemRangeChanged(positionStart, itemCount))
            override fun onItemRangeInserted(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = addAdapterAction(NotifyItemRangeInserted(positionStart, itemCount))
            override fun onItemRangeChanged(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = addAdapterAction(NotifyItemRangeChanged(positionStart, itemCount))
        })
    }

    private fun addAdapterAction(action: AdapterAction) {
        adapterActionQueue.offer(action)
        adapterAction.value = adapterActionQueue
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<Queue<AdapterAction>>) {
        adapterAction.observe(lifecycleOwner, observer)
    }
}
