package com.example.healthyandfoodclean.fragment

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.HomeActivity
import com.example.healthyandfoodclean.R

class ListenerActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var buttonHome : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listener)
        buttonHome = findViewById(R.id.buttonHome)
        webView = findViewById(R.id.webView)
        setupWebView()
        buttonHome.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }
        // Load danh sách nhạc từ Zing MP3
        loadMusicList()
    }
//cau hinh hien thi web
    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
    }
//tai danh sach nhac
    private fun loadMusicList() {
        // Thay đổi URL dưới đây để truy cập vào danh sách nhạc từ Zing MP3
        val url = "https://www.zingmp3.vn"

        webView.loadUrl(url)
    }
}
