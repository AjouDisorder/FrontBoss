package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.Home.Mart.MartAdapter
import com.realmealboss.realmeal.Home.Mart.MartModel
import com.realmealboss.realmeal.LoginActivity
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_mart_list.*
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
        setContentView(R.layout.activity_mart_list)

        settingPermission()

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        val resIdList = ArrayList<String>()
        val resTitleList = ArrayList<String>()
        val martList = ArrayList<MartModel>()

        val adapter = MartAdapter(martList, R.layout.item_mart)

        iMyService.getRestaurant(BossData.getOid()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("레스토랑 불러오기 실패")
                println(t.toString())
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var result = response.body()?.string()
                println(result)

                var jsonArray = JSONArray(result)
                for (i in 0..(jsonArray.length()-1)){
                    var jsonObject:JSONObject = jsonArray.getJSONObject(i)
                    var _id = jsonObject.getString("_id")
                    var title = jsonObject.getString("title")
                    var type = jsonObject.getString("type")
                    resIdList.add(_id)
                    resTitleList.add(title)
                    // 마트정보 GET
                    martList.add(i, MartModel(R.drawable.img_bob, title, type))
                    martListView.adapter = adapter
                    martListView.layoutManager = LinearLayoutManager(this@MartListActivity, RecyclerView.VERTICAL, false)
                    //martListView.layoutManager = GridLayoutManager(this, 2)
                }
            }
        })

        adapter.onItemSelectionChangedListener = {
            println("select : $it")
            BossData.setROid(resIdList.get(it.toInt()))
            BossData.setRTitle(resTitleList.get(it.toInt()))
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        //martListView.adapter = adapter
        //martListView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        //martListView.layoutManager = GridLayoutManager(this, 2)

        mart_add_button.setOnClickListener{
            val intent = Intent(this, LicenseCheckActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun settingPermission(){
        var permis = object  : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                //Toast.makeText(this@MartListActivity, "권한 허가", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MartListActivity, "권한 거부", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.finishAffinity(this@MartListActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        println("nope")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
