package com.realmealboss.realmeal.Home.Sales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.Home.Order.OrderModel
import com.realmealboss.realmeal.Home.Order.OrderViewHolder
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.item_ticket.view.*

class SalesAdapter (val list:List<OrderModel>): RecyclerView.Adapter<OrderViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ticket, parent, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.containerView.tv_ticketTitle.text = list[position].title
        holder.containerView.tv_ticketPrice.text = list[position].price.toString()
        holder.containerView.tv_ticketUser.text = list[position].userName
        holder.containerView.tv_ticketMethod.text = list[position].method
        holder.containerView.tv_ticketValue.text = list[position].value
    }

}