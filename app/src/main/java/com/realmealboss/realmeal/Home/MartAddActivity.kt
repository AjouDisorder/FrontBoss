package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.realmealboss.realmeal.*
import com.realmealboss.realmeal.Retrofit.*
import kotlinx.android.synthetic.main.activity_mart_add.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartAddActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_add)

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
        println(BossData.getOid())

        mart_address_button.setOnClickListener{
            println(BossData.getOid())
            val intent = Intent(this, SearchAddressActivity::class.java)
            startActivity(intent)
            finish()
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
            println(BossData.getOid())


            iMyService.createRestaurant(BossData.getOid(), name, type, address, phone, intro, lat.toDouble(), lng.toDouble()).enqueue(object : Callback<ResponseBody> {
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

                    val intent = Intent(this@MartAddActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@MartAddActivity, MartListActivity::class.java)
        startActivity(intent)
        finish()
    }


}
