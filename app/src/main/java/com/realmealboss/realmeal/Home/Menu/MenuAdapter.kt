package com.realmealboss.realmeal.Home.Menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_menu_list.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter (val list: List<MenuModel>): RecyclerView.Adapter<MenuViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.containerView.tv_menuTitle.text = list[position].title
        holder.containerView.tv_menuType.text = list[position].type
        holder.containerView.tv_originPrice.text = list[position].price.toString()

    }

}