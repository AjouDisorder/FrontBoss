package com.realmealboss.realmeal.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.realmealboss.realmeal.BossData
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val storage = Firebase.storage
        val storageReference = storage.reference
        val pathReference = storageReference.child("marts/${BossData.getROid()}.jpg")

        pathReference.downloadUrl.addOnSuccessListener {uri ->
            println(uri.toString())
            Glide.with(this).load(uri.toString()).into(mart_home_img)
        }.addOnFailureListener{}

        //val httpsReference = storage.getReferenceFromUrl(storageUrl.toString())

        mart_home_title.text = BossData.getRTitle()

        mart_info_button.setOnClickListener{
            val intent = Intent(this, MartInfoActivity::class.java)
            startActivity(intent)
            finish()
        }
        menu_info_button.setOnClickListener{
            val intent = Intent(this, MenuListActivity::class.java)
            startActivity(intent)
            finish()
        }
        promotion_info_button.setOnClickListener{
            val intent = Intent(this, PromoteListActivity::class.java)
            startActivity(intent)
            finish()
        }
        order_details_button.setOnClickListener{
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
            finish()
        }
        sales_details_button.setOnClickListener{
            val intent = Intent(this, SalesListActivity::class.java)
            startActivity(intent)
            finish()
        }

//        qrCodeScan.setOnClickListener{
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
//                Toast.makeText(this, "취소!!", Toast.LENGTH_LONG).show()
//            } else {
//                //Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
//                Toast.makeText(this, "식권인증완료", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MartListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
