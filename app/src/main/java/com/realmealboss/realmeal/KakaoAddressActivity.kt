package com.realmealboss.realmeal

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.realmealboss.realmeal.Home.MartInfoActivity


class KakaoAddressActivity : AppCompatActivity() {

    companion object {
        val webView: WebView? = null
        var txt_address: TextView? = null
        var handler: Handler? = Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_address)

        // WebView 초기화
        init_webView()

        // 핸들러를 통한 JavaScript 이벤트 반응
        //handler = Handler()
    }

    fun init_webView(){
        // WebView 설정
        val webView:WebView = findViewById(R.id.webView_address)

        // JavaScript 허용
        webView.settings.javaScriptEnabled = true

        // JavaScript의 window.open 허용
        webView.settings.javaScriptCanOpenWindowsAutomatically = true

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(AndroidBridge(), "TestApp")

        // web client 를 chrome 으로 설정
        webView.webChromeClient = WebChromeClient()

        // webview url load. php 파일 주소
        webView.loadUrl("http://49.50.172.13/kakao_address.php")
    }

    private class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String?, arg2: String?, arg3: String?){
            handler?.post(Runnable{
                fun run(){
                    txt_address!!.text = (String.format("(%s) %s %s", arg1, arg2, arg3))

                }
            })
        }
    }
}