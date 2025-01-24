package com.jetbrains.moneygenie.expects

import android.content.Context
import android.widget.Toast
import com.jetbrains.moneygenie.MoneyGenieApp

/**
 * Created by Kundan on 24/01/25
 **/
actual fun showMessage(message: String, isLongToast: Boolean) {
    val context: Context = MoneyGenieApp.getContext()
    Toast.makeText(context, message, if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT)
        .show()
}