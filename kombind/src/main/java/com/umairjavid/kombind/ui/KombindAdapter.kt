package com.umairjavid.kombind.ui

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.umairjavid.kombind.BR
import com.umairjavid.kombind.model.AdapterAction
import com.umairjavid.kombind.model.MutableLiveArrayList

abstract class KombindAdapter<V: KombindAdapter.ViewHolder>(private val items: MutableLiveArrayList<*>) : RecyclerView.Adapter<V>() {
    private lateinit var layoutInflater: LayoutInflater
    open val handler: Any? = null
    protected abstract fun getLayout(position: Int): Int

    fun registerObserver(lifecycleOwner: LifecycleOwner) {
        items.observe(lifecycleOwner, Observer {
            while(it?.isNotEmpty() == true) {
                val action = it.remove()
                when (action) {
                    is AdapterAction.NotifyItemRangeInserted -> notifyItemRangeInserted(action.positionStart, action.itemCount)
                    is AdapterAction.NotifyItemRangeRemoved -> notifyItemRangeRemoved(action.positionStart, action.itemCount)
                    is AdapterAction.NotifyItemRangeChanged -> notifyItemRangeChanged(action.positionStart, action.itemCount)
                    AdapterAction.NotifyDataSetChanged -> notifyDataSetChanged()
                }
            }
        })
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = getLayout(position)

    protected fun getItem(position: Int): Any? = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return createViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
    }

    @Suppress("UNCHECKED_CAST")
    open fun createViewHolder(viewDataBinding: ViewDataBinding) = ViewHolder(viewDataBinding) as V

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bind(items[position], handler)
    }

    open class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: Any, handler: Any?) {
            binding.setVariable(BR.item, item)
            if (handler != null) binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
        }
    }
}
