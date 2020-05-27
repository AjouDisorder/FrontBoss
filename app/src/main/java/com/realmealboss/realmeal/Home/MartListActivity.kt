package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Mart.MartAdapter
import com.realmealboss.realmeal.Home.Mart.MartModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.Restaurant
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_mart_create.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MartListActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_create)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        mart_add_button.setOnClickListener{
            val intent = Intent(this, MartInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
        val resIdList = ArrayList<String>()
        val resTitleList = ArrayList<String>()

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
                    var title = jsonObject.getString("title")
                    println(_id)
                    resIdList.add(_id)
                    resTitleList.add(title)
                    println(resIdList)
                    println(resTitleList)
                }
            }

        })

        val martList = listOf(
            MartModel(R.drawable.dume, "두메산골")
            //, MartModel(R.drawable.oddugi, "오뚜기")
            //, MartModel(R.drawable.shyepo, "셰프의포차")
            //, MartModel(R.drawable.ta_u, "커피타유")
            )

        val adapter = MartAdapter(martList, R.layout.item_mart)

        adapter.onItemSelectionChangedListener = {
            println("select : $it")
            BossData.setROid(resIdList.get(it.toInt()))
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        martListView.adapter = adapter
        martListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        //martListView.layoutManager = GridLayoutManager(this, 2)

    }
}
