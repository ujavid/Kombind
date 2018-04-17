package com.umairjavid.kombind.model

sealed class AdapterAction {
    class NotifyItemRangeInserted(val positionStart: Int, val itemCount: Int) : AdapterAction()
    class NotifyItemRangeRemoved(val positionStart: Int, val itemCount: Int) : AdapterAction()
    class NotifyItemRangeChanged(val positionStart: Int, val itemCount: Int) : AdapterAction()
    object NotifyDataSetChanged : AdapterAction()
}
