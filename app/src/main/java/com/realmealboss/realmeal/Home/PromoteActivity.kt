package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Menu.MenuModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.ResponseDTO
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_mart_add.*
import kotlinx.android.synthetic.main.activity_menu_list.*
import kotlinx.android.synthetic.main.activity_promote.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class OriginMenuModel(val id:String, val title:String, val price:Number, val type:String)
class PromoteActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promote)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val menuList = ArrayList<OriginMenuModel>()
        val titleList = ArrayList<String>()
        val selected:Int = 0

        iMyService.getRestaurant(BossData.getOid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("실패")
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var id:String
                var title:String
                var price:Number
                var type:String


                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length()-1)){
                    var jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    var _id = jsonObject.getString("_id")
                    if(BossData.getROid() == _id){
                        var originMenuArray = jsonObject.getJSONArray("originMenuList")
                        for(j in 0..(originMenuArray.length()-1)){
                            var originMenu = originMenuArray.getJSONObject(j)
                            title = originMenu.getString("title")
                            price = originMenu.getInt("originPrice")
                            type = originMenu.getString("type")
                            id = originMenu.getString("_id")

                            menuList.add(j, OriginMenuModel(id,title,price,type))
                            titleList.add(j, title+" / "+price+"원")
                        }
                    }
                }
                val originMenuAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,titleList.toArray())
                promote_menu_select.adapter = originMenuAdapter
            }
        })
        promote_menu_select.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

        pro_menu_submit.setOnClickListener{
            var title: String
            var price: Number
            var type: String
            var id: String
            var discount: String
            var quantity: String = pro_menu_limit.text.toString()
            var method: String
            var start_year: String
            var start_month: String
            var start_date: String
            var start_hour: String
            var start_min: String
            var end_year: String
            var end_month: String
            var end_date: String
            var end_hour: String
            var end_min: String
            //Check empty


        }

    }


    
}
