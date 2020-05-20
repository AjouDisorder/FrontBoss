package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Toast
import com.realmealboss.realmeal.*
import com.realmealboss.realmeal.Retrofit.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_mart_info.*
import kotlinx.android.synthetic.main.activity_search_address.*
import okhttp3.ResponseBody
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

        if (intent.hasExtra("address")){
            val address = intent.getStringExtra("address")
            mart_address.setText(address)
        }else{}

        //---------
        var lat = "37.537187"
        var lng = "127.005476"

        if (intent.hasExtra("lat")){
            lat = intent.getStringExtra("lat")
        }else{}
        if (intent.hasExtra("lng")){
            lng = intent.getStringExtra("lng")
        }else{}

        //---------

        mart_address_button.setOnClickListener{
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivity(intent)
        }

        mart_info_submit.setOnClickListener{
            var name = mart_name.text.toString()
            var type = "meal"
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
            Toast.makeText(this, lat +","+ lng, Toast.LENGTH_SHORT).show()

            /*
            iMyService.createRestaurant(BossData.getBid(), name, type, address, phone, intro, lat, lng).enqueue(object : Callback<ResponseDTO> {
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
            })*/


        }

    }


}
