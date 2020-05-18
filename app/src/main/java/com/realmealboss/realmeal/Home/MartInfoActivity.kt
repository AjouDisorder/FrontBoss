package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.KakaoAddressActivity
import com.realmealboss.realmeal.LoginActivity
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.ResponseDTO
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_mart_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartInfoActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_info)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        mart_address.setOnClickListener{
            val intent = Intent(this, KakaoAddressActivity::class.java)
            startActivity(intent)
        }

        mart_info_submit.setOnClickListener{
            var name = mart_name.text.toString()
            var address = mart_address.text.toString()
            var phone = mart_phone.text.toString()
            var intro = mart_intro.text.toString()

            //Check empty
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"가게명을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(address)){
                Toast.makeText(this,"가게주소를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(this,"전화번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            iMyService.createRestaurant(BossData.getBid(), name, address, phone).enqueue(object : Callback<ResponseDTO> {
                override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ResponseDTO>?,
                    response: Response<ResponseDTO>?
                ) {
                    Toast.makeText(this@MartInfoActivity,response?.body().toString(), Toast.LENGTH_SHORT).show()
                    println(response?.body().toString())
                    val intent = Intent(this@MartInfoActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            })
        }

    }
}
