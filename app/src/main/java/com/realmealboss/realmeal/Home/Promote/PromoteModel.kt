package com.realmealboss.realmeal.Home.Promote

data class PromoteModel(
    val title:String,
    val type:String,
    val discount:Number,
    val originPrice:Number,
    val price:Number,
    val start_time:String,
    val end_time:String,
    val quantity:Number,
    val method:String,
    val _id:String

)