package com.realmealboss.realmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.ResponseDTO
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_join.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        // click button
        join_accept_button.setOnClickListener {

            var bossId = join_id.text.toString()
            var password = join_password.text.toString()
            var name = join_name.text.toString()

            //Check empty
            if(TextUtils.isEmpty(bossId)){
                Toast.makeText(this,"Email can not be null or empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"Password can not be null or empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"Name can not be null or empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            iMyService.joinBoss(bossId, password, name).enqueue(object : Callback<ResponseDTO> {
                override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ResponseDTO>?,
                    response: Response<ResponseDTO>?
                ) {
                    Toast.makeText(this@JoinActivity,response?.body().toString(), Toast.LENGTH_SHORT).show()
                    println(response?.body().toString())
                }
            })
        }
    }
}
