package com.umairjavid.kombind.model

import android.content.Intent
import com.umairjavid.kombind.ui.DialogFragmentBuilder

data class ViewAction(val action: Action,
                      val intent: Intent? = null,
                      val requestCode: Int = 0,
                      val builder: DialogFragmentBuilder<*>? = null,
                      val v4builder: com.umairjavid.kombind.ui.v4.DialogFragmentBuilder<*>? = null,
                      val tag: String = "") {
    enum class Action {
        START_ACTIVITY,
        START_ACTIVITY_FOR_RESULT,
        SHOW_DIALOG,
        DISMISS_DIALOG,
        DISMISS_DIALOG_WITH_TAG,
        FINISH
    }
}
