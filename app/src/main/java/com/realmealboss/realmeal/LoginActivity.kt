package com.realmealboss.realmeal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService
    internal var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Init API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        //event
        login_button.setOnClickListener{
            loginUser(edt_email.text.toString(),edt_password.text.toString())
        }

        join_button.setOnClickListener {
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.register_layout,null)

            MaterialStyledDialog.Builder(this)
                .setIcon(R.drawable.ic_user)
                .setTitle("REGISTRATION")
                .setDescription("Please fill all fields")
                .setCustomView(itemView)
                .setNegativeText("CANCEL")
                .onNegative { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveText("REGISTER")
                .onPositive(MaterialDialog.SingleButtonCallback{_, _ ->
                    val edt_email = itemView.findViewById<View>(R.id.edt_email) as MaterialEditText
                    val edt_name = itemView.findViewById<View>(R.id.edt_name) as MaterialEditText
                    val edt_password = itemView.findViewById<View>(R.id.edt_password) as MaterialEditText
                        
                    if(TextUtils.isEmpty(edt_email.text.toString())){
                        Toast.makeText(this,"Email can not be null or empty",Toast.LENGTH_SHORT).show()
                        return@SingleButtonCallback;
                    }
                    
                    if(TextUtils.isEmpty(edt_name.text.toString())){
                        Toast.makeText(this,"Name can not be null or empty",Toast.LENGTH_SHORT).show()
                        return@SingleButtonCallback;
                    }
                    if(TextUtils.isEmpty(edt_password.text.toString())){
                        Toast.makeText(this,"Password can not be null or empty",Toast.LENGTH_SHORT).show()
                        return@SingleButtonCallback;
                    }
                    registerUser(edt_email.text.toString(),edt_name.text.toString(),edt_password.text.toString())

                })
        }
    }

    private fun registerUser(email: String, name: String, password: String) {
        compositeDisposable.add(iMyService.registerUser(email,name,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{result ->
                Toast.makeText(this,""+result,Toast.LENGTH_SHORT).show()
            })
    }

    private fun loginUser(email: String, password: String) {

        //Check empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Email can not be null or empty",Toast.LENGTH_SHORT).show()
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password can not be null or empty",Toast.LENGTH_SHORT).show()
            return;
        }


        compositeDisposable.add(iMyService.loginUser(email,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{result ->
                Toast.makeText(this,""+result,Toast.LENGTH_SHORT).show()
            })
    }

}
