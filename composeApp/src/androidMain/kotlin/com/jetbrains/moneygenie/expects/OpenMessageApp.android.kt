package com.jetbrains.moneygenie.expects

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.jetbrains.moneygenie.MoneyGenieApp

/**
 * Created by Kundan on 16/01/25
 **/
actual fun openMessageApp(message: String) {
    val context: Context = MoneyGenieApp.getContext()

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }

    val chooser = Intent.createChooser(intent, "Choose an app to send message")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    context.startActivity(chooser)
}