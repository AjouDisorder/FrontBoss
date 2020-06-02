package com.realmealboss.realmeal.Home.Promote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.R

class PromoteAdapter (val list:List<PromoteModel>):RecyclerView.Adapter<PromoteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promote, parent, false)
        return PromoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: PromoteViewHolder, position: Int) {
        //holder.containerView.tv_menuTitle.text = list[position].title

    }

}