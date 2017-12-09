package com.umairjavid.kombind.model

import android.content.Intent
import com.umairjavid.kombind.ui.DialogFragmentBuilder

sealed class ViewAction {
    class StartActivity(val intent: Intent) : ViewAction()
    class StartActivityForResult(val intent: Intent, val requestCode: Int) : ViewAction()
    class ShowDialog(val builder: DialogFragmentBuilder<*>): ViewAction()
    class ShowV4Dialog(val builder: com.umairjavid.kombind.ui.v4.DialogFragmentBuilder<*>): ViewAction()
    class DismissDialog(val tag: String = ""): ViewAction()
    object Finish: ViewAction()
}

