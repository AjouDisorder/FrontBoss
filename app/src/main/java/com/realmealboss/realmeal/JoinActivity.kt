package com.realmealboss.realmeal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
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
            var bossToken = FirebaseInstanceId.getInstance().getToken().toString();

            //Check empty
            if(TextUtils.isEmpty(bossId)){
                Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(this,"비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            iMyService.joinBoss(bossId, password, name, bossToken).enqueue(object : Callback<ResponseDTO> {
                override fun onFailure(call: Call<ResponseDTO>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ResponseDTO>?,
                    response: Response<ResponseDTO>?
                ) {
                    Toast.makeText(this@JoinActivity,response?.body().toString(), Toast.LENGTH_SHORT).show()
                    println(response?.body().toString())
                    if(response?.body()?.result == "signup success") {
                        dup_text.text = ""
                        join_id.setBackgroundResource(R.drawable.white_edittext)
                        val intent = Intent(this@JoinActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        dup_text.text = "이미 존재하는 아이디 입니다."
                        join_id.setBackgroundResource(R.drawable.red_edittext)
                    }
                }
            })
        }
    }
}
