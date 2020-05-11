package com.realmealboss.realmeal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.realmealboss.realmeal.Retrofit.ResponseDTOex
import com.realmealboss.realmeal.Retrofit.TestService
import kotlinx.android.synthetic.main.activity_rtrofit_test.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RtrofitTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rtrofit_test)

        //192.168.25.30:3000

        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.25.30:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server =retrofit.create(TestService::class.java)

        button_get.setOnClickListener{
            server.getRequest("messi").enqueue(object:Callback<ResponseDTOex>{
                override fun onFailure(call: Call<ResponseDTOex>?, t: Throwable?) {

                }
                override fun onResponse(call: Call<ResponseDTOex>?, response: Response<ResponseDTOex>?) {
                    println(response?.body().toString())
                }
            })
        }

        button_get_param.setOnClickListener{
            server.getParamRequest("board01").enqueue(object:Callback<ResponseDTOex>{
                override fun onFailure(call: Call<ResponseDTOex>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ResponseDTOex>?, response: Response<ResponseDTOex>?) {
                    println(response?.body().toString())
                }

            })
        }

        button_post.setOnClickListener{
            server.postRequest("test","1234").enqueue(object:Callback<ResponseDTOex>{
                override fun onFailure(call: Call<ResponseDTOex>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ResponseDTOex>?, response: Response<ResponseDTOex>?) {
                    println(response?.body().toString())
                }

            })
        }

        button_update.setOnClickListener{
            server.putRequest("board01", "Edit Edit").enqueue(object:Callback<ResponseDTOex>{
                override fun onFailure(call: Call<ResponseDTOex>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ResponseDTOex>?, response: Response<ResponseDTOex>?) {
                    println(response?.body().toString())
                }

            })
        }

        button_delete.setOnClickListener{
            server.deleteRequest("board01").enqueue(object:Callback<ResponseDTOex>{
                override fun onFailure(call: Call<ResponseDTOex>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ResponseDTOex>?, response: Response<ResponseDTOex>?) {
                    println(response?.body().toString())
                }

            })
        }

    }
}
