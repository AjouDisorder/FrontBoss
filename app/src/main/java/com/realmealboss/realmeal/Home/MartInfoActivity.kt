package com.realmealboss.realmeal.Home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.R
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import com.realmealboss.realmeal.SearchAddressActivity
import com.realmealboss.realmeal.SearchEditAddressActivity
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
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MartInfoActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    var REQUEST_IMAGE_CAPTURE = 2
    var currentPhotoPath: String = ""
    lateinit var tempSelectFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mart_info)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        mart_info_address_button.setOnClickListener{
            println(BossData.getOid())
            val intent = Intent(this, SearchEditAddressActivity::class.java)
            startActivity(intent)
            finish()
        }
        mart_info_img_button.setOnClickListener{
            startUpload()
        }
        mart_info_cam_button.setOnClickListener{
            startCapture()
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
                        for (k in 0..(items.size-1)){
                            if (type == items.get(k)){
                                mart_info_type.setSelection(k)
                                break
                            }
                        }
                    }
                }
                if (intent.hasExtra("address")){
                    val address = intent.getStringExtra("address")
                    mart_info_address.setText(address)
                }else{}
                if (intent.hasExtra("lat")){
                    lat = intent.getStringExtra("lat")
                }else{}
                if (intent.hasExtra("lng")){
                    lng = intent.getStringExtra("lng")
                }else{}
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

                    val storage = Firebase.storage
                    val storageRef = storage.reference
                    val imagesRef: StorageReference? = storageRef.child("marts/${_id}.jpg")

                    mart_info_img.isDrawingCacheEnabled = true
                    mart_info_img.buildDrawingCache()
                    val bitmap = (mart_info_img.drawable as BitmapDrawable).bitmap
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val data = baos.toByteArray()

                    var uploadTask = imagesRef?.putBytes(data)
                    uploadTask?.addOnFailureListener{
                        println("firebase err")
                    }?.addOnSuccessListener {
                        println("firebase success")
                        val intent = Intent(this@MartInfoActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })
        }
    }

    fun startCapture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch(ex: IOException){
                    null
                }
                photoFile?.also{
                    val photoURI : Uri = FileProvider.getUriForFile(
                        this,
                        "com.realmealboss.realmeal.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
    fun startUpload(){
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)
    }
    @Throws(IOException::class)
    private fun createImageFile() : File{
        val timeStamp : String = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpeg",
            storageDir
        ).apply{
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            val dataUri = data?.getData()
            mart_info_img.setImageURI(dataUri)

            try {
                val input = dataUri?.let { contentResolver.openInputStream(it) }
                val image = BitmapFactory.decodeStream(input)
                mart_info_img.setImageBitmap(image)
                input?.close()

                val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Date())
                val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                if(!storageDir!!.exists()){
                    Log.i("mCurrentPhotoPath1", storageDir.toString())
                    storageDir.mkdirs()
                }
                tempSelectFile = File(storageDir,
                    "JPEG_" + date + ".jpeg")
                val output: OutputStream = FileOutputStream(tempSelectFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, output)
                currentPhotoPath = tempSelectFile.absolutePath
            }catch (ioe: IOException){
                ioe.printStackTrace()
            }
            /*
            try {
                val input = dataUri?.let { contentResolver.openInputStream(it) }
                val image = BitmapFactory.decodeStream(input)
                mart_add_img.setImageBitmap(image)
                input?.close()

                val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Date())
                val storageDir = File(Environment.getExternalStorageDirectory().toString()+"/Pictures/Test")
                if(!storageDir.exists()){
                    Log.i("mCurrentPhotoPath1", storageDir.toString())
                    storageDir.mkdirs()
                }
                tempSelectFile = File(storageDir,
                    "temp_" + date + ".jpeg")
                val output:OutputStream = FileOutputStream(tempSelectFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, output)
            }catch (ioe:IOException){
                ioe.printStackTrace()
            }*/

        }

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val file = File(currentPhotoPath)
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(file))
                mart_info_img.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                val bitmap = ImageDecoder.decodeBitmap(decode)
                mart_info_img.setImageBitmap(bitmap)
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

}
