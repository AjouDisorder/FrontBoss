package com.realmealboss.realmeal.Retrofit

import android.provider.ContactsContract
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null, var bossId: String? = null)
data class ResponseBInfo(var result: String? = null, var _id: String? = null, var bossId: String? = null)
data class Restaurant(var result:ArrayList<String>? = null)

interface IMyService{
    @FormUrlEncoded
    @POST("boss/signup")
    fun joinBoss(@Field("bossId") bossId: String,
                 @Field("password") password: String,
                 @Field("name") name: String
        //@Field("age") age: String,
        //@Field("sex") sex: String,
        //@Field("phone") phone: String
    ): Call<ResponseDTO>
    @FormUrlEncoded
    @POST("boss/login")
    fun loginBoss(@Field("bossId") id: String,
                  @Field("password") password: String): Call<ResponseBInfo>

    @FormUrlEncoded
    @POST("boss/createRestaurant")
    fun createRestaurant(@Field("boss_id") id: String,
                         @Field("title") title: String,
                         @Field("address") address: String,
                         @Field("phone") phone: String
    ): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("boss/createOriginMenu")
    fun createOriginMenu(@Field("boss_id") id: String,
                         @Field("title") title: String,
                         @Field("price") price: String,
                         @Field("description") description: String
    ): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("boss/createMenu")
    fun createMenu(@Field("boss_id") id: String,
                   @Field("title") title: String,
                   @Field("discountedPrice") price: String,
                   @Field("quantity") quantity: String): Call<ResponseDTO>

}