package com.realmealboss.realmeal.Home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import java.util.*
import kotlin.collections.ArrayList

data class OriginMenuModel(val id:String, val title:String, val price:Number, val type:String)
class PromoteActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    val calendar: Calendar = Calendar.getInstance()
    val cal = Calendar.getInstance()
    val currentTime = calendar.timeInMillis

    var ay = calendar.get(Calendar.YEAR)
    var am = calendar.get(Calendar.MONTH)
    var ad = calendar.get(Calendar.DAY_OF_MONTH)
    var ah = cal.get(Calendar.HOUR_OF_DAY)
    var ami = cal.get(Calendar.MINUTE)
    var by = calendar.get(Calendar.YEAR)
    var bm = calendar.get(Calendar.MONTH)
    var bd = calendar.get(Calendar.DAY_OF_MONTH)
    var bh = cal.get(Calendar.HOUR_OF_DAY)
    var bmi = cal.get(Calendar.MINUTE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promote)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val menuList = ArrayList<OriginMenuModel>()
        val titleList = ArrayList<String>()
        var selected = 0

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

//        val calendar: Calendar = Calendar.getInstance()
//        val cal = Calendar.getInstance()
//        val currentTime = calendar.timeInMillis
//
//        var ay = calendar.get(Calendar.YEAR)
//        var am = calendar.get(Calendar.MONTH)
//        var ad = calendar.get(Calendar.DAY_OF_MONTH)
//        var ah = cal.get(Calendar.HOUR_OF_DAY)
//        var ami = cal.get(Calendar.MINUTE)
//        var ay:Int
//        var am:Int
//        var ad:Int
//        var ah:Int
//        var ami:Int
//        var by:Int
//        var bm:Int
//        var bd:Int
//        var bh:Int
//        var bmi:Int

        pro_date_a_button.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                pro_date_a.setText(""+year+"."+(month+1)+"."+dayOfMonth)
                ay = year
                am = month+1
                ad = dayOfMonth
            }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            .apply { datePicker.minDate = System.currentTimeMillis() }
            .show()
        }
        pro_date_b_button.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                    pro_date_b.setText(""+year+"."+(month+1)+"."+dayOfMonth)
                    by = year
                    bm = month+1
                    bd = dayOfMonth
                }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .apply { datePicker.minDate = System.currentTimeMillis() }
                .show()
        }
        pro_time_a_button.setOnClickListener{
            val cal = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(this,
            TimePickerDialog.OnTimeSetListener{view, hourOfDay,minute->
                pro_time_a.setText(""+hourOfDay+":"+minute)
                ah = hourOfDay
                ami = minute
            },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true)
            timePickerDialog.show()
        }
        pro_time_b_button.setOnClickListener{
            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener{view, hourOfDay,minute->
                    pro_time_b.setText(""+hourOfDay+":"+minute)
                    bh = hourOfDay
                    bmi = minute
                },cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),true)
            timePickerDialog.show()
        }

        //pro_datePicker_a.minDate = System.currentTimeMillis()
        //pro_datePicker_b.minDate = System.currentTimeMillis()
        //pro_datePicker_a.minDate = currentTime
        //pro_datePicker_b.minDate = currentTime
        //pro_datePicker_a.maxDate = nextYear
        //pro_datePicker_b.maxDate = nextYear

        pro_menu_submit.setOnClickListener{
            var title: String = promote_menu_select.selectedItem.toString()
            selected = titleList.indexOf(title)
            var price: Number = menuList[selected].price.toInt()
            var type: String = menuList[selected].type
            var id: String = menuList[selected].id
            var discount: Number = pro_menu_price.text.toString().toInt()
            var quantity: Number = pro_menu_quantity.text.toString().toInt()
            var method: String
            if (pro_menu_forhere.isChecked && pro_menu_takeout.isChecked){
                method = "both"
            }else if (pro_menu_takeout.isChecked){
                method = "takeout"
            }else if (pro_menu_forhere.isChecked){
                method = "forhere"
            }else{ return@setOnClickListener }
            var start_year: String = ay.toString()
            var start_month: String = am.toString()
            var start_date: String = ad.toString()
            var start_hour: String = ah.toString()
            var start_min: String = ami.toString()
            var end_year: String = by.toString()
            var end_month: String = bm.toString()
            var end_date: String = bd.toString()
            var end_hour: String = bh.toString()
            var end_min: String = bmi.toString()

            iMyService.createMenu(id,discount,quantity,method,start_year,start_month,start_date,start_hour,start_min,end_year,end_month,end_date,end_hour,end_min)
                .enqueue(object : Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        iMyService.topicSend(BossData.getROid(),title)
                        val intent = Intent(this@PromoteActivity, PromoteListActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                })
        }

    }
    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, PromoteListActivity::class.java)
        startActivity(intent)
        finish()
    }


    
}
