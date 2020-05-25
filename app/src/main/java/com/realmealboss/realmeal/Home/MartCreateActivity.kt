package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.Home.Mart.MartAdapter
import com.realmealboss.realmeal.Home.Mart.MartModel
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_mart_create.*

class MartCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_create)

        mart_add_button.setOnClickListener{
            val intent = Intent(this, MartInfoActivity::class.java)
            startActivity(intent)
        }

        val martList = listOf(
            MartModel(
                R.drawable.dume,
                "두메산골"
            ),
            MartModel(
                R.drawable.oddugi,
                "오뚜기"
            ),
            MartModel(
                R.drawable.shyepo,
                "셰프의포차"
            ),
            MartModel(
                R.drawable.ta_u,
                "커피타유"
            )
            )

        val adapter = MartAdapter(
            martList,
            R.layout.item_mart
        )

        martListView.adapter = adapter
        martListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        //martListView.layoutManager = GridLayoutManager(this, 2)

        martListView.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}
