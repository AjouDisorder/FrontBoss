package com.realmealboss.realmeal.Home

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.realmealboss.realmeal.*
import com.realmealboss.realmeal.Retrofit.*
import kotlinx.android.synthetic.main.activity_mart_add.*
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class MartAddActivity : AppCompatActivity() {

    var REQUEST_IMAGE_CAPTURE = 2

    lateinit var iMyService: IMyService
    lateinit var currentPhotoPath: String
    lateinit var tempSelectFile: File


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
        mart_add_img_button.setOnClickListener{
            startUpload()
        }
        mart_add_cam_button.setOnClickListener{
            startCapture()
        }

        mart_info_submit.setOnClickListener{
            var name = mart_name.text.toString()
            var type = mart_type.text.toString()
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

            val file = File(currentPhotoPath)
            //val requestUserId = RequestBody.create(MediaType.parse("multipart/form-data"),BossData.getROid())
            var requestImage: MultipartBody.Part? = null
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            requestImage = MultipartBody.Part.createFormData("img", file.getName(), requestFile)

            println(requestFile)
            println(requestImage)

            iMyService.createPicture(requestImage).enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    println("실패")
                    println(t?.message.toString())
                }
                override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: Response<ResponseBody>?
                ) {
                    var result = response?.body()?.string()
                    println(result)

                }
            })

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

    fun startCapture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch(ex:IOException){
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
            mart_add_img.setImageURI(dataUri)

            try {
                val input = dataUri?.let { contentResolver.openInputStream(it) }
                val image = BitmapFactory.decodeStream(input)
                mart_add_img.setImageBitmap(image)
                input?.close()

                val date = SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(Date())
                val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                if(!storageDir!!.exists()){
                    Log.i("mCurrentPhotoPath1", storageDir.toString())
                    storageDir.mkdirs()
                }
                tempSelectFile = File(storageDir,
                    "JPEG_" + date + ".jpeg")
                val output:OutputStream = FileOutputStream(tempSelectFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, output)
                currentPhotoPath = tempSelectFile.absolutePath
            }catch (ioe:IOException){
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
                mart_add_img.setImageBitmap(bitmap)
            } else {
                val decode = ImageDecoder.createSource(
                    this.contentResolver,
                    Uri.fromFile(file)
                )
                val bitmap = ImageDecoder.decodeBitmap(decode)
                mart_add_img.setImageBitmap(bitmap)
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@MartAddActivity, MartListActivity::class.java)
        startActivity(intent)
        finish()
    }


}
