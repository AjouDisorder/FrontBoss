package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import com.realmealboss.realmeal.SearchAddressActivity
import kotlinx.android.synthetic.main.activity_mart_add.*
import kotlinx.android.synthetic.main.activity_mart_info.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MartInfoActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_info)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        mart_info_address_button.setOnClickListener{
            println(BossData.getOid())
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivity(intent)
            finish()
        }

        val items = resources.getStringArray(R.array.res_type)
        val typeAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,items)
        mart_info_type.adapter = typeAdapter
        mart_info_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    else -> {
                    }
                }
            }
        }

        var lat = "31"
        var lng = "128"
        iMyService.getRestaurant(BossData.getOid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length()-1)){
                    var jsonObject:JSONObject = jsonArray.getJSONObject(i)
                    var _id = jsonObject.getString("_id")
                    if (_id == BossData.getROid()){
                        var title = jsonObject.getString("title")
                        var type = jsonObject.getString("type")
                        var description = jsonObject.getString("description")
                        var address = jsonObject.getString("address")
                        var phone = jsonObject.getString("phone")
                        var location = jsonObject.getJSONObject("location")
                        var coordinate = location.getString("coordinates")
                        println(coordinate)
                        var arr = coordinate.split("[","]",",")
                        lng = arr[1]
                        lat = arr[2]
                        //lng = jsonObject.getString("lng")
                        //lat = jsonObject.getString("lat")

                        mart_info_name.setText(title)
                        mart_info_intro.setText(description)
                        mart_info_address.setText(address)
                        mart_info_phone.setText(phone)
                    }
                }
            }
        })

        mart_edit_info_submit.setOnClickListener{
            var name = mart_info_name.text.toString()
            var type = mart_info_type.selectedItem.toString()
            var address = mart_info_address.text.toString()
            var phone = mart_info_phone.text.toString()
            var intro = mart_info_intro.text.toString()

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

            iMyService.updateRestaurant(BossData.getROid(), name, type, address, phone, intro, lat.toDouble(), lng.toDouble()).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                }
                override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: Response<ResponseBody>?
                ) {
                    var result = response?.body()?.string()
                    var jsonObject = JSONObject(result)
                    var _id = jsonObject.getString("_id")
                    var title = jsonObject.getString("title")

                    BossData.setROid(_id)
                    BossData.setRTitle(title)

                    val intent = Intent(this@MartInfoActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
