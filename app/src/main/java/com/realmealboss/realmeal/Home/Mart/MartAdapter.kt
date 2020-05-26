package com.realmealboss.realmeal.Home.Mart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_mart.view.*

class MartAdapter(val list:List<MartModel>, val layoutId: Int): RecyclerView.Adapter<MartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        val viewHolder = MartViewHolder(view)
        view.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                val id = v?.tag/*
                if(selectionList.contains(id)) selectionList.remove(id)
                else selectionList.add(id as Long)
                notifyDataSetChanged()
                onItemSelectionChangedListener?.let{it(selectionList)}
                */ //중복선택
                notifyDataSetChanged()
                onItemSelectionChangedListener?.let{it(id as Long)}
            }
        })
        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MartViewHolder, position: Int) {
        holder.containerView.martImage.setImageResource(list[position].ImageId)
        holder.containerView.martName.text = list[position].name

        holder.containerView.tag = getItemId(position)
        holder.containerView.isActivated = selectionList.contains(getItemId(position))

    }

    override fun getItemId(position: Int): Long {
        //return super.getItemId(position)
        return position.toLong()
    }

    val selectionList = mutableListOf<Long>()
    //var onItemSelectionChangedListener:((MutableList<Long>)->Unit)? = null // 중복선택
    var onItemSelectionChangedListener:((Long)->Unit)? = null

}