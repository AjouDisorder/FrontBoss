package com.realmealboss.realmeal.Retrofit

import android.provider.ContactsContract
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

data class ResponseDTO(var result:String? = null, var bossId: String? = null)
data class ResponseBInfo(var result: String? = null, var _id: String? = null, var bossId: String? = null)
data class Restaurant(var documents: List<ResDocument>)
data class ResDocument(var _id: String? = null)
data class ResPosition(var documents:String? = null, var address:String? = null, var y: String? = null, var x:String? = null)

interface IMyService{
    //회원가입 로그인
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

    //가게정보관리
    @FormUrlEncoded
    @POST("boss/createRestaurant")
    fun createRestaurant(@Field("boss_id") id: String,
                         @Field("title") title: String,
                         @Field("type") type: String,
                         @Field("address") address: String,
                         @Field("phone") phone: String,
                         @Field("description") intro: String,
                         @Field("lat") lat : Double,
                         @Field("lng") lng : Double
    ): Call<ResponseBody>

    @GET("/boss/getRestaurantList")
    fun getRestaurant(@Query("boss_id") id:String): Call<ResponseBody>

    @Multipart
    @POST("/createPicture")
    fun createPicture(@Part img: MultipartBody.Part?): Call<ResponseBody>

    //가게메뉴관리
    @FormUrlEncoded
    @POST("boss/createOriginMenu")
    fun createOriginMenu(@Field("restaurant_id") id: String,
                         @Field("title") title: String,
                         @Field("originPrice") price: Number,
                         @Field("type") type: String
    ): Call<ResponseBody>


    //홍보글 관리
    @FormUrlEncoded
    @POST("boss/createMenu")
    fun createMenu(@Field("boss_id") id: String,
                   @Field("title") title: String,
                   @Field("discountedPrice") price: String,
                   @Field("quantity") quantity: String): Call<ResponseDTO>

    //주문관리
    //판매내역관리

}