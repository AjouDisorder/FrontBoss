package com.realmealboss.realmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class KakaoAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_address)

        val addressWebView: WebView = findViewById(R.id.webView_address)
        addressWebView.loadUrl("http://www.naver.com")
    }
}
