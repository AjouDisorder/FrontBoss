package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.ResponseDTO
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_mart_info.*
import kotlinx.android.synthetic.main.activity_promote.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromoteActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promote)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        pro_menu_submit.setOnClickListener{
            var name = pro_menu_name.text.toString()
            var price = pro_menu_price.text.toString()
            var quantity = pro_menu_limit.text.toString()

            //Check empty
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"메뉴명을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(price)){
                Toast.makeText(this,"메뉴가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            iMyService.createMenu(BossData.getBid(), name, price, quantity).enqueue(object :
                Callback<ResponseDTO> {
                override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ResponseDTO>?,
                    response: Response<ResponseDTO>?
                ) {
                    Toast.makeText(this@PromoteActivity,response?.body().toString(), Toast.LENGTH_SHORT).show()
                    println(response?.body().toString())
                    val intent = Intent(this@PromoteActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            })
        }

    }
}
