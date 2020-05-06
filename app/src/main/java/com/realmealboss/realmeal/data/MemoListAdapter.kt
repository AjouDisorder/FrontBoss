package com.realmealboss.realmeal.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.item_memo.view.*
import java.text.SimpleDateFormat

class MemoListAdapter(private val list: MutableList<MemoData>)// 생성자에서 MutableList를 받음
    : RecyclerView.Adapter<ItemViewHolder>(){

    // Date 객체를 사람이 볼수 있는 문자열로 변환하기 위한 객체
    private val dateFormat = SimpleDateFormat("MM/dd HH:mm")

    // item_memo를 불러 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo,parent,false)
        return ItemViewHolder(view)
    }

    // list내의 MemoData의 개수를 반환
    override fun getItemCount(): Int {
        return list.count()
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if(list[position].title.isNotEmpty()){ // titleView에 제목 표시
            holder.containerView.titleView.visibility = View.VISIBLE
            holder.containerView.titleView.text = list[position].title
        }
        else {
            holder.containerView.titleView.visibility = View.GONE // INVISIBLE : 내용만 감추기
        }
        holder.containerView.summaryView.text = list[position].summary // 요약내용
        holder.containerView.dateView.text = dateFormat.format(list[position].createdAt) // 날짜
    }

}