package com.example.healthyandfoodclean.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.HomeActivity
import com.example.healthyandfoodclean.R

class ChatGPTActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var inputEditText: EditText
    private lateinit var sendButton: Button

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_gptactivity)

        webView = findViewById(R.id.webView)
        inputEditText = findViewById(R.id.inputEditText)
        sendButton = findViewById(R.id.sendButton)

        // Cấu hình WebView
        webView.settings.javaScriptEnabled = true

        // Tải trang ChatGPT
        webView.loadUrl("https://chat.openai.com/")

        // Xử lý khi người dùng nhấn Enter trên bàn phím hoặc nhấn nút Gửi
        inputEditText.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND || (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        sendButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }

        // Đảm bảo các liên kết trong WebView mở trong WebView chứ không mở bên ngoài ứng dụng
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        // Hiển thị tiến trình tải trang trong thanh tiêu đề
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) {
                    supportActionBar?.subtitle = null
                } else {
                    supportActionBar?.subtitle = "Loading..."
                }
            }
        }
    }


    // Xử lý sự kiện back trên WebView
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // Xử lý khi Activity bị hủy
    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}