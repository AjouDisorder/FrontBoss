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
        promotion_info_button.setOnClickListener{
            val intent = Intent(this, PromoteActivity::class.java)
            startActivity(intent)
        }
        order_details_button.setOnClickListener{
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
        sales_details_button.setOnClickListener{
            val intent = Intent(this, SalesActivity::class.java)
            startActivity(intent)
        }
    }
}
