package com.realmealboss.realmeal.Home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Order.OrderAdapter
import com.realmealboss.realmeal.Home.Order.OrderModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_order_list.*
import kotlinx.android.synthetic.main.item_ticket.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderListActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val orderList = ArrayList<OrderModel>()

        val adapter = OrderAdapter(orderList)

        iMyService.getPaidTicketList(BossData.getROid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var title:String
                var price:Number
                var userName:String
                var method:String
                var value:String

                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length()-1)){
                    var jsonObject: JSONObject = jsonArray.getJSONObject(i)
                    title = jsonObject.getString("title")
                    price = jsonObject.getInt("totalPrice")
                    userName = jsonObject.getString("userName")
                    method = jsonObject.getString("method")
                    if(method=="takeout"){
                        method="방문포장"
                        tv_ticketMethod.setBackgroundColor("#431F63".toInt())
                    }else if(method=="forhere"){
                        method="매장식사"
                        tv_ticketMethod.setBackgroundColor("#FF9FF3".toInt())
                    }else{
                        method="나는둘다"
                        tv_ticketMethod.setBackgroundColor("#4febe3".toInt())
                    }
                    value = jsonObject.getString("value")

                    orderList.add(i, OrderModel(title,price,userName,method, value))
                    orderListView.adapter = adapter
                    orderListView.layoutManager = LinearLayoutManager(this@OrderListActivity, RecyclerView.VERTICAL, false)
                }
            }
        })


//        qrScan.setOnClickListener{
//            IntentIntegrator(this).initiateScan()
//        }
    }



//    // Get the results:
//    override fun onActivityResult(
//        requestCode: Int,
//        resultCode: Int,
//        data: Intent?
//    ) {
//        val result =
//            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//        if (result != null) {
//            if (result.contents == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

}
