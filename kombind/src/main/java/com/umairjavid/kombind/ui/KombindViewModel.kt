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

    fun action(viewAction: ViewAction) {
       addViewAction(viewAction)
    }
}
