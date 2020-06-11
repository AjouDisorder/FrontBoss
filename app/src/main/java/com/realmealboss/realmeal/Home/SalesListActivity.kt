package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Order.OrderAdapter
import com.realmealboss.realmeal.Home.Order.OrderModel
import com.realmealboss.realmeal.Home.Sales.SalesAdapter
import com.realmealboss.realmeal.Home.Sales.SalesModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.activity_sales_list.*
import kotlinx.android.synthetic.main.item_ticket.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SalesListActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales_list)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val salesList = ArrayList<SalesModel>()

        val adapter = SalesAdapter(salesList)

        iMyService.getCertifiedTicketList(BossData.getROid()).enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var title: String
                var price: Number
                var userName: String
                var method: String
                var value: String
                var quantity: Number

                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length() - 1)) {
                    var jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    title = jsonObject.getString("menuName")
                    price = jsonObject.getInt("totalPrice")
                    userName = jsonObject.getString("userName")
                    method = jsonObject.getString("method")
                    quantity = jsonObject.getInt("quantity")

                    if (method == "takeout") {
                        method = "방문포장"
                    } else if (method == "forhere") {
                        method = "매장식사"
                    } else {
                        method = "나는둘다"
                    }
                    value = jsonObject.getString("value")
                    var _id = jsonObject.getString("_id")
                    println("2")
                    salesList.add(i, SalesModel(title, price, userName, method, value, quantity))
                    salesListView.adapter = adapter
                    salesListView.layoutManager = LinearLayoutManager(this@SalesListActivity, RecyclerView.VERTICAL, false)
                }
            }
        })
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
