package com.realmealboss.realmeal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.data.ListViewModel
import com.realmealboss.realmeal.data.MemoListAdapter
import kotlinx.android.synthetic.main.fragment_memo_list.*

/**
 * A simple [Fragment] subclass.
 */
class MemoListFragment : Fragment() {

    // adapter, listviewmodel을 담을 속성을 선언
    private lateinit var listAdapter: MemoListAdapter
    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //  activity!! 추가
        viewModel = activity!!.application!!.let {
            ViewModelProvider(
                activity!!.viewModelStore,
                ViewModelProvider.AndroidViewModelFactory(it))
                .get(ListViewModel::class.java) // get으로 받는다
        }

        viewModel!!.let {
            // MemoLiveData를 가져와서 Adapter에 담아 RecyclerView에 출력하도록 함
            it.memoLiveData.value?.let {
                listAdapter = MemoListAdapter(it)
                memoListView.layoutManager =
                    LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
                memoListView.adapter = listAdapter
            }
            // MemoLiveData에 observe 함수를 통해 값이 변할 때 동작할 Observer를 붙여줌
            // Observer 내에서는 adapter의 갱신 코드를 호출
            it.memoLiveData.observe(this,
                Observer{
                    listAdapter.notifyDataSetChanged()
                })
        }

    }

}
