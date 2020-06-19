package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_license_check.*
import kotlinx.android.synthetic.main.activity_mart_list.*

class LicenseCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license_check)

        license_submit.setOnClickListener{

            Toast.makeText(this, "인증 완료", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MartAddActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, MartListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
