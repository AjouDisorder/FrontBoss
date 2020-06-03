package com.realmealboss.realmeal.Home.Mart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.item_mart.view.*

class MartAdapter(val list:List<MartModel>, val layoutId: Int): RecyclerView.Adapter<MartViewHolder>() {

    val restaurantTypeToIcons = hashMapOf("뷔페&샐러드" to R.drawable.store_buffet, "술집" to R.drawable.store_drink,
        "편의점" to R.drawable.store_convstore, "한식" to R.drawable.store_korean, "치킨" to R.drawable.store_chicken, "피자" to R.drawable.store_pizza,
        "족발&보쌈" to R.drawable.store_jokbal, "돈까스&일식&회" to R.drawable.store_japan, "양식&아시안" to R.drawable.store_american,
        "패스트푸드" to R.drawable.store_fastfood, "분식" to R.drawable.store_snack, "카페&디저트" to R.drawable.store_dessert,
        "찜&탕&찌개" to R.drawable.store_soup, "도시락" to R.drawable.store_dosirak, "중국집" to R.drawable.store_china)

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
        //holder.containerView.martImage.setImageResource(list[position].ImageId)
        holder.containerView.martImage.setImageResource(restaurantTypeToIcons[list[position].type]!!)

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