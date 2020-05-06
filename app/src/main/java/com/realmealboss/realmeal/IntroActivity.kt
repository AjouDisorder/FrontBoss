package com.realmealboss.realmeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

class IntroActivity : AppCompatActivity() {

    //Handler : Runnable을 실행하는 클래스
    //Runnable : 병렬 실행이 가능한 Thread를 만들어 주는 클래스
    var handler: Handler? = null
    var runnable: Runnable? = null

    override fun onResume() {
        super.onResume()
        // Runnable이 실행되면 ListActivity로 이동하는 코드
        //                      LoginActivity
        runnable = Runnable {
            val intent = Intent(applicationContext, ListActivity::class.java)
            startActivity(intent)
        }
        // Handler를 생성하고 2000ms(2초)후 runnable을 실행
        handler = Handler()
        handler?.run{
            postDelayed(runnable, 2000)
        }
    }
    override fun onPause() {// Activity Pause 상태일 때 runnable도 중단
        super.onPause()

        handler?.removeCallbacks(runnable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //첫 인트로 화면 띄울때 전체 화면으로 띄우기
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        //


    }
}
