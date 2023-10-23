package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView

@Suppress("DEPRECATION")
class ContestDetailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest_detailed)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        // URL 가져오기
        val contestUrl = intent.getStringExtra("contest_url")
        val fullUrl = "https://www.wevity.com/" + contestUrl

        webView.webViewClient = WebViewClient()

        // URL을 WebView에 로드
        if (contestUrl != null) {
            webView.loadUrl(fullUrl)
        } else {
            // contestUrl이 null인 경우 처리할 내용을 여기에 추가
        }

    }
}
