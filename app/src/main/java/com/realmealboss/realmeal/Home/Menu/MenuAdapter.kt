package com.realmealboss.realmeal.Home.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.activity_menu_list.view.*
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuAdapter (val list: List<MenuModel>): RecyclerView.Adapter<MenuViewHolder>(){
    val menuTypeToIcons = hashMapOf( "치킨&피자" to R.drawable.menu_chickenpizza, "족발&보쌈" to R.drawable.menu_jokbal,
        "돈까스&일식" to R.drawable.menu_japan, "세계음식" to R.drawable.menu_nation, "햄버거" to R.drawable.menu_hambur,
        "밥류" to R.drawable.menu_rice, "카페&빵&디저트" to R.drawable.menu_cafe, "육고기" to R.drawable.menu_meat,
        "면" to R.drawable.menu_noodle, "분식&야식" to R.drawable.menu_snack, "찜&탕&찌개" to R.drawable.menu_soup,
        "반찬&과일" to R.drawable.menu_fruit, "떡&기타" to R.drawable.menu_ricecake,
        "샐러드&다이어트" to R.drawable.menu_salad, "편의점" to R.drawable.menu_convstore)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        val viewHolder = MenuViewHolder(view)
        println("1")
        view.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                val id = v?.tag

                println("2")
                onItemSelectionChangedListener?.let{it(id as Long)}
            }
        })

        return viewHolder
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.containerView.iv_menuPictureOrigin.setImageResource(menuTypeToIcons[list[position].type]!!)
        holder.containerView.tv_menuTitleOrigin.text = list[position].title
        holder.containerView.tv_menuTypeOrigin.text = list[position].type
        holder.containerView.tv_originPriceOrigin.text = list[position].price.toString()

        holder.containerView.tag = getItemId(position)
        holder.containerView.isActivated = selectionList.contains(getItemId(position))

    }
    override fun getItemId(position: Int): Long {
        //return super.getItemId(position)
        return position.toLong()
    }

    val selectionList = mutableListOf<Long>()
    var onItemSelectionChangedListener:((Long)->Unit)? = null
}