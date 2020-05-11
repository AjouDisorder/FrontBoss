package com.realmealboss.realmeal

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.realmealboss.realmeal.Retrofit.IMyService
import com.realmealboss.realmeal.Retrofit.RetrofitClient
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    lateinit var iMyService: IMyService

    //private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //callback = SessionCallback()
        //Session.getCurrentSession().addCallback(callback)
       //Session.getCurrentSession().checkAndImplicitOpen()

        // Init retrofit API
        val retrofit = RetrofitClient.getInstance()
        iMyService = retrofit.create(IMyService::class.java)

        //event
        login_button.setOnClickListener{
            loginUser(edt_email.text.toString(),edt_password.text.toString())
        }

        kakao_login_button.setOnClickListener {
            Toast.makeText(this,"Kakao",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, KakaoInfoActivity::class.java)
            startActivity(intent)
            finish()
        }

        join_button.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(id: String, password: String) {

        //Check empty
        if(TextUtils.isEmpty(id)){
            Toast.makeText(this,"Email can not be null or empty",Toast.LENGTH_SHORT).show()
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Password can not be null or empty",Toast.LENGTH_SHORT).show()
            return;
        }
    }

    // 앱의 해쉬 키 얻는 함수
    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }

    }
/*
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
        return
    }
    super.onActivityResult(requestCode, resultCode, data)
}

override fun onDestroy() {
    super.onDestroy()
    Session.getCurrentSession().removeCallback(callback)
}

private inner class SessionCallback : ISessionCallback {
    override fun onSessionOpened() {
        // 로그인 세션이 열렸을 때
        UserManagement.getInstance().me( object : MeV2ResponseCallback() {
            override fun onSuccess(result: MeV2Response?) {
                // 로그인이 성공했을 때
                var intent = Intent(this@LoginActivity, KakaoInfoActivity::class.java)
                intent.putExtra("name", result!!.getNickname())
                intent.putExtra("profile", result!!.getProfileImagePath())
                startActivity(intent)
                finish()
            }

            override fun onSessionClosed(errorResult: ErrorResult?) {
                // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                Toast.makeText(
                    this@LoginActivity,
                    "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onSessionOpenFailed(exception: KakaoException?) {
        // 로그인 세션이 정상적으로 열리지 않았을 때
        if (exception != null) {
            com.kakao.util.helper.log.Logger.e(exception)
            Toast.makeText(
                this@LoginActivity,
                "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception",
                Toast.LENGTH_SHORT).show()
        }
    }

}

private fun redirectSignupActivity() {
    val intent = Intent(this, LoginActivity::class.java)
    startActivity(intent)
    finish()
}
*/
}
