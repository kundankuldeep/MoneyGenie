package com.jetbrains.moneygenie.expects

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.jetbrains.moneygenie.theme.Primary700

/**
 * Created by Kundan on 02/02/25
 **/
@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun LoadWebView(url: String) {
    var isLoading by remember { mutableStateOf(true) }
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    isLoading = false // Hide loader when page loads
                }
            }
            loadUrl(url)
        }
    })

    // Show loader while page is loading
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.7f)), // Semi-transparent background
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Primary700) // Your primary color
        }
    }
}