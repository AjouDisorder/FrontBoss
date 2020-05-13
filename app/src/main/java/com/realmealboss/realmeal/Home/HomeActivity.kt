package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.realmealboss.realmeal.LoginActivity
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mart_info_button.setOnClickListener{
            val intent = Intent(this, MartInfoActivity::class.java)
            startActivity(intent)
        }
        menu_info_button.setOnClickListener{
            val intent = Intent(this, MenuInfoActivity::class.java)
            startActivity(intent)
        }
    }
}
