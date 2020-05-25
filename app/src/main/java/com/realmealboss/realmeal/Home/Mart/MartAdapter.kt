package com.realmealboss.realmeal.Home.Mart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_mart.view.*

class MartAdapter(val list:List<MartModel>, val layoutId: Int): RecyclerView.Adapter<MartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return MartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MartViewHolder, position: Int) {
        holder.containerView.martImage.setImageResource(list[position].ImageId)
        holder.containerView.martName.text = list[position].name

    }

}