package com.umairjavid.kombind.ui

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.content.res.Resources
import com.umairjavid.kombind.model.ViewAction
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

abstract class KombindViewModel(application: Application) : AndroidViewModel(application) {
    private val viewActionQueue = LinkedBlockingQueue<ViewAction>()
    val viewAction = MutableLiveData<Queue<ViewAction>>()
    val context by lazy { application }
    val resources: Resources = context.resources
    lateinit var activityViewModel: Any

    private fun addViewAction(action: ViewAction) {
        viewActionQueue.offer(action)
        viewAction.value = viewActionQueue
    }

    protected fun startActivity(intent: Intent) {
        addViewAction(ViewAction.StartActivity(intent))
    }

    protected fun startActivityForResult(intent: Intent, requestCode: Int) {
        addViewAction(ViewAction.StartActivityForResult(intent, requestCode))
    }

    protected fun showDialog(builder: DialogFragmentBuilder<*>) {
        addViewAction(ViewAction.ShowDialog(builder))
    }

    protected fun showDialog(builder: com.umairjavid.kombind.ui.v4.DialogFragmentBuilder<*>) {
        addViewAction(ViewAction.ShowV4Dialog(builder))
    }

    protected fun dismissDialog() {
        addViewAction(ViewAction.DismissDialog())
    }

    protected fun dismissDialog(tag: String) {
        addViewAction(ViewAction.DismissDialog(tag))
    }

    protected fun finish() {
        addViewAction(ViewAction.Finish)
    }
}
