package com.jetbrains.moneygenie.expects

/**
 * Created by Kundan on 16/01/25
 **/
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun openMessageApp(message: String) {
    val url = "sms:?&body=${message.encodeURLParameter()}"
    val nsUrl = NSURL(string = url)

    if (UIApplication.sharedApplication.canOpenURL(nsUrl)) {
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}

// Helper function to encode special characters in URLs
fun String.encodeURLParameter(): String {
    return this.replace(" ", "%20")
}