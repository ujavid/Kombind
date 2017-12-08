package com.umairjavid.kombind.ui

import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.umairjavid.kombind.BR

abstract class KombindAdapter<T: Any, V: KombindAdapter.ViewHolder>(private val items: ObservableArrayList<T>) : RecyclerView.Adapter<V>() {
    open val handler: Any? = null
    protected abstract fun getLayout(position: Int): Int

    init {
        items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<T>>() {
            override fun onItemRangeRemoved(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = notifyItemRangeRemoved(positionStart, itemCount)
            override fun onChanged(sender: ObservableArrayList<T>?) = notifyDataSetChanged()
            override fun onItemRangeMoved(sender: ObservableArrayList<T>?, positionStart: Int, toPosition: Int, itemCount: Int) = notifyItemRangeChanged(positionStart, itemCount)
            override fun onItemRangeInserted(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = notifyItemRangeInserted(positionStart, itemCount)
            override fun onItemRangeChanged(sender: ObservableArrayList<T>?, positionStart: Int, itemCount: Int) = notifyItemRangeChanged(positionStart, itemCount)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, position: Int): V =
            createViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent?.context), getLayout(position), parent, false))

    @Suppress("UNCHECKED_CAST")
    open fun createViewHolder(viewDataBinding: ViewDataBinding): V {
        return ViewHolder(viewDataBinding) as V
    }

    override fun onBindViewHolder(holder: V?, position: Int) {
        holder?.bind(items[position], handler)
    }

    override fun getItemCount() = items.size

    open class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: Any, handler: Any?) {
            binding.setVariable(BR.item, item)
            if (handler != null) binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
        }
    }
}
