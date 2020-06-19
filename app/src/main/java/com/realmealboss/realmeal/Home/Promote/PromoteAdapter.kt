package com.realmealboss.realmeal.Home.Promote

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.realmealboss.realmeal.R
import kotlinx.android.synthetic.main.item_mart.view.*
import kotlinx.android.synthetic.main.item_promote.view.*

class PromoteAdapter (val list:List<PromoteModel>):RecyclerView.Adapter<PromoteViewHolder>(){
    val menuTypeToIcons = hashMapOf( "치킨&피자" to R.drawable.menu_chickenpizza, "족발&보쌈" to R.drawable.menu_jokbal,
        "돈까스&일식" to R.drawable.menu_japan, "세계음식" to R.drawable.menu_nation, "햄버거" to R.drawable.menu_hambur,
        "밥류" to R.drawable.menu_rice, "카페&빵&디저트" to R.drawable.menu_cafe, "육고기" to R.drawable.menu_meat,
        "면" to R.drawable.menu_noodle, "분식&야식" to R.drawable.menu_snack, "찜&탕&찌개" to R.drawable.menu_soup,
        "반찬&과일" to R.drawable.menu_fruit, "떡&기타" to R.drawable.menu_ricecake,
        "샐러드&다이어트" to R.drawable.menu_salad, "편의점" to R.drawable.menu_convstore)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_promote, parent, false)
        return PromoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: PromoteViewHolder, position: Int) {


        val storage = Firebase.storage
        val storageReference = storage.reference
        val pathReference = storageReference.child("promotes/${list[position]._id}.jpg")
        pathReference.downloadUrl.addOnSuccessListener {uri ->
            Glide.with(holder.containerView)
                .load(uri.toString())
                .transition(DrawableTransitionOptions.withCrossFade(700))
                .into(holder.containerView.iv_menuPicture)
        }.addOnFailureListener{
            holder.containerView.iv_menuPicture.setImageResource(menuTypeToIcons[list[position].type]!!)
            holder.containerView.iv_menuPicture.startAnimation(AnimationUtils.loadAnimation(holder.containerView.context, R.anim.fadein))
        }

        holder.containerView.tv_menuTitle.text = list[position].title
        holder.containerView.tv_discount.text = list[position].discount.toString()
        holder.containerView.tv_discountedPrice.text = list[position].price.toString()
        holder.containerView.tv_quantity.text = list[position].quantity.toString()
        holder.containerView.tv_originPrice.text = list[position].originPrice.toString()
        holder.containerView.tv_startTime.text = list[position].start_time
        holder.containerView.tv_endTime.text = list[position].end_time

    }

}