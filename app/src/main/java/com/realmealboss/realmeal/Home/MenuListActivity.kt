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
import com.realmealboss.realmeal.Home.Menu.MenuAdapter
import com.realmealboss.realmeal.Home.Menu.MenuModel
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.ResponseDTO
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_menu_list.*
import kotlinx.android.synthetic.main.activity_menu_list.menu_name
import kotlinx.android.synthetic.main.activity_menu_list.menu_price
import kotlinx.android.synthetic.main.item_menu.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MenuListActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_list)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)


        val menuList = ArrayList<MenuModel>()
        val menuIdList = ArrayList<String>()

        val adapter = MenuAdapter(menuList)

        iMyService.getRestaurant(BossData.getOid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

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
                            var menuOid = originMenu.getString("_id")

                            menuIdList.add(j,menuOid)
                            menuList.add(j, MenuModel(title,price,type))
                            menuListView.adapter = adapter
                            menuListView.layoutManager = LinearLayoutManager(this@MenuListActivity, RecyclerView.VERTICAL, false)
                        }
                    }
                }
            }
        })

        adapter.onItemSelectionChangedListener = {
            println("select : $it")
            println(menuIdList.get(it.toInt()))
            iMyService.deleteOriginMenu(menuIdList.get(it.toInt())).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var result = response.body()?.string()
                    println(result)
                    val intent = Intent(this@MenuListActivity, MenuListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }


        //Spinner
        val items = resources.getStringArray(R.array.menu_type)
        val typeAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,items)
        menu_type.adapter = typeAdapter
        menu_type.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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

        menu_add.setOnClickListener{

            var name = menu_name.text.toString()
            var price : Number = menu_price.text.toString().toInt()
            var type = menu_type.selectedItem.toString()

            //Check empty
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this,"메뉴명을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(price.toString())){
                Toast.makeText(this,"메뉴가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            iMyService.createOriginMenu(BossData.getROid(), name, price, type).enqueue(object :
                Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: Response<ResponseBody>?
                ) {
                    println(response?.body().toString())
                    val intent = Intent(this@MenuListActivity, MenuListActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

//    fun addMenu(){
//        val builder = AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
//
//        val inflater = layoutInflater
//        val view = inflater.inflate(R.layout.dialog_menu, null)
//        println("1")
//        builder.setView(view)
//        println("2")
//        val dialog = builder.create()
//        println("3")
//        menu_add.setOnClickListener {
//            var name = menu_name.text.toString()
//            var price = menu_price.text.toString()
//            println("3")
//            Toast.makeText(this, name+"/"+price,Toast.LENGTH_LONG).show()
//            println("4")
//            dialog.dismiss()
//        }
//        println("5")
//        dialog.show()
//    }
}
