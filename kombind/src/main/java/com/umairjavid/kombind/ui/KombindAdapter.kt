package com.umairjavid.kombind.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.umairjavid.kombind.BR
import com.umairjavid.kombind.model.AdapterAction
import com.umairjavid.kombind.model.MutableLiveArrayList

abstract class KombindAdapter<VH: KombindAdapter.ViewHolder>(protected val items: MutableLiveArrayList<*>) : RecyclerView.Adapter<VH>() {
    private lateinit var layoutInflater: LayoutInflater
    protected abstract fun getLayout(position: Int): Int
    open fun getHandler(position: Int): Any? = null

    fun registerObserver(lifecycleOwner: LifecycleOwner): KombindAdapter<VH> {
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
        return this
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = getLayout(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return createViewHolder(DataBindingUtil.inflate(layoutInflater, viewType, parent, false))
    }

    @Suppress("UNCHECKED_CAST")
    open fun createViewHolder(viewDataBinding: ViewDataBinding) = ViewHolder(viewDataBinding) as VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], getHandler(position))
    }

    open class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(item: Any, handler: Any?) {
            binding.setVariable(BR.item, item)
            if (handler != null) binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
        }
    }
}
