package com.jetbrains.moneygenie.expects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIView
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

/**
 * Created by Kundan on 02/02/25
 **/
@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun LoadWebView(url: String) {
    UIKitView(
        factory = {
            webViewView(url)
        },
        modifier = Modifier
    )
}

@OptIn(ExperimentalForeignApi::class)
fun webViewView(url: String): UIView {
    val webView = WKWebView(
        frame = CGRectMake(0.0, 0.0, 0.0, 0.0), // Initial frame, auto-layout will handle resizing
        configuration = WKWebViewConfiguration()
    )
    webView.loadRequest(NSURLRequest(NSURL(string = url)))

    return webView
}