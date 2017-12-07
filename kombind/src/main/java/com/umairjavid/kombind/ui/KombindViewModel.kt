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
        addViewAction(ViewAction(action = ViewAction.Action.START_ACTIVITY, intent = intent))
    }

    protected fun startActivityForResult(intent: Intent, requestCode: Int) {
        addViewAction(ViewAction(action = ViewAction.Action.START_ACTIVITY_FOR_RESULT, intent = intent, requestCode = requestCode))
    }

    protected fun showDialog(builder: DialogFragmentBuilder<*>) {
        addViewAction(ViewAction(action = ViewAction.Action.SHOW_DIALOG, builder = builder))
    }

    protected fun showDialog(builder: com.umairjavid.kombind.ui.v4.DialogFragmentBuilder<*>) {
        addViewAction(ViewAction(action = ViewAction.Action.SHOW_DIALOG, v4builder = builder))
    }

    protected fun dismissDialog() {
        addViewAction(ViewAction(action = ViewAction.Action.DISMISS_DIALOG))
    }

    protected fun dismissDialog(tag: String) {
        addViewAction(ViewAction(action = ViewAction.Action.DISMISS_DIALOG_WITH_TAG, tag = tag))
    }

    protected fun finish() {
        addViewAction(ViewAction(ViewAction.Action.FINISH))
    }
}
