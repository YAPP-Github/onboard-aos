package com.yapp.bol.presentation.view.group.join

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import android.widget.TextView
import com.yapp.bol.presentation.R

class WelcomeJoinDialog(
    context: Context,
    user: String,
) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_welcome_user)
        findViewById<TextView>(R.id.tv_welcome).text = user
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
