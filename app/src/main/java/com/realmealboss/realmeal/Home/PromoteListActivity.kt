package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Promote.PromoteAdapter
import com.realmealboss.realmeal.Home.Promote.PromoteModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_promote_list.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PromoteListActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promote_list)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val promoteList = ArrayList<PromoteModel>()

        val adapter = PromoteAdapter(promoteList)

        iMyService.getMenu(BossData.getROid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var title:String
                var type:String
                var discount:Number
                var originPrice:Number
                var price:Number
                var start_time:String
                var end_time:String
                var quantity:Number
                var method:String

                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length()-1)){
                    var jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    title = jsonObject.getString("title")
                    type = jsonObject.getString("type")
                    quantity = jsonObject.getInt("quantity")
                    discount = jsonObject.getInt("discount")
                    originPrice = jsonObject.getJSONObject("originMenu").getInt("originPrice")
                    price = originPrice - (originPrice*discount/100)
                    start_time = jsonObject.getString("startDateObject").substring(11,16)
                    end_time = jsonObject.getString("endDateObject").substring(11,16)
                    method = jsonObject.getString("method")

                    promoteList.add(i, PromoteModel(title,type,discount,originPrice,price,start_time,end_time,quantity,method))
                    promoteListView.adapter = adapter
                    promoteListView.layoutManager = LinearLayoutManager(this@PromoteListActivity, RecyclerView.VERTICAL, false)
                }
            }
        })




        promote_add_button.setOnClickListener{

            val intent = Intent(this, PromoteActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
