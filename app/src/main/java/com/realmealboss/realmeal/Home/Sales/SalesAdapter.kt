package com.realmealboss.realmeal.Home.Sales

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.Home.Order.OrderModel
import com.realmealboss.realmeal.Home.Order.OrderViewHolder
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.item_ticket.view.*

class SalesAdapter (val list:List<SalesModel>): RecyclerView.Adapter<SalesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return SalesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: SalesViewHolder, position: Int) {
        holder.containerView.tv_ticketTitle.text = list[position].title
        holder.containerView.tv_ticketPrice.text = list[position].price.toString()
        holder.containerView.tv_ticketUser.text = list[position].userName
        holder.containerView.tv_ticketMethod.text = list[position].method
        if(list[position].method == "매장 식사") {
            holder.containerView.tv_ticketMethod.setBackgroundColor(Color.parseColor("#431F63"))
        }else if(list[position].method == "방문 포장"){
            holder.containerView.tv_ticketMethod.setBackgroundColor(Color.parseColor("#FF9FF3"))
        }else{
            holder.containerView.tv_ticketMethod.setBackgroundColor(Color.parseColor("#fffeee"))
        }
        holder.containerView.tv_ticketValue.text = list[position].value
        holder.containerView.tv_ticketQuantity.text = list[position].quantity.toString()

    }

}