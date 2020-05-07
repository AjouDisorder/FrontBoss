package com.realmealboss.realmeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide;
import kotlinx.android.synthetic.main.activity_kakao_info.*

class KakaoInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_info)

        tvNickname.text = intent.getStringExtra("name")
        tvProfile.text = intent.getStringExtra("profile")

        Glide.with(this)
            .load(intent.getStringExtra("profile"))
            .into(imgProfile)

        kakao_info_button.setOnClickListener{
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }
}
