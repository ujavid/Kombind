package com.umairjavid.kombind.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.umairjavid.kombind.BR

abstract class KombindListAdapter<T, VH: KombindListAdapter.ViewHolder<T>>(protected val items: LiveData<List<T>>,
                                                                           callback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(callback) {
    private lateinit var layoutInflater: LayoutInflater
    protected abstract fun getLayout(position: Int): Int
    open fun getHandler(position: Int): Any? = null

    fun registerObserver(lifecycleOwner: LifecycleOwner): KombindListAdapter<T, VH> {
        items.observe(lifecycleOwner, Observer { submitList(it) })
        return this
    }

    override fun getItemCount() = items.value?.size ?: 0

    override fun getItemViewType(position: Int) = getLayout(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return createViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
    }

    @Suppress("UNCHECKED_CAST")
    open fun createViewHolder(viewDataBinding: ViewDataBinding) = ViewHolder<T>(viewDataBinding) as VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items.value?.get(position), getHandler(position))
    }

    open class ViewHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: T?, handler: Any?) {
            binding.setVariable(BR.item, item)
            if (handler != null) binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
        }
    }
}
