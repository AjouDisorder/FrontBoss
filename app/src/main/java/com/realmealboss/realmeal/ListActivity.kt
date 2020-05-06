package com.realmealboss.realmeal

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.realmealboss.realmeal.data.ListViewModel
import com.realmealboss.realmeal.data.MemoData

import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ListActivity : AppCompatActivity() {

    private var viewModel: ListViewModel? = null // ListViewModel을 담을 변수를 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        //추가 클릭하면 DetailActivity로 이동
        fab.setOnClickListener { view ->
            //val intent = Intent(applicationContext, DetailActivity::class.java)
            //startActivity(intent)
            viewModel!!.let {
                var memoData = MemoData()
                memoData.title = "제목 테스트"
                memoData.summary = "요약내용 테스트"
                memoData.createdAt = Date()

                it.addMemo(memoData)
            }
        }

        // MemoListFragment를 화면에 표시
        val fragmentTransation = supportFragmentManager.beginTransaction()
        fragmentTransation.replace(R.id.contentLayout, MemoListFragment())
        fragmentTransation.commit()
        // ListViewModel을 가져오는 코드    app에서 생성된다   provider 생성필요
        viewModel = application!!.let {
            ViewModelProvider(viewModelStore, ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java) // get으로 받는다
        }

    }

}
