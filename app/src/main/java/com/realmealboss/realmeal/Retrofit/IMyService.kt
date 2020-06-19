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
                 @Field("name") name: String,
                 @Field("bossToken") bossToken: String
        //@Field("age") age: String,
        //@Field("sex") sex: String,
        //@Field("phone") phone: String
    ): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("boss/login")
    fun loginBoss(@Field("bossId") id: String,
                  @Field("password") password: String,
                  @Field("token") token : String
    ): Call<ResponseBInfo>

    @FormUrlEncoded
    @POST("sendPush2")
    fun topicSend(@Field("topic") topic: String,
                  @Field("menu") menu: String): Call<ResponseBody>


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

    @FormUrlEncoded
    @POST("/boss/updateRestaurant")
    fun updateRestaurant(@Field("restaurant_id") id:String,
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

    @FormUrlEncoded
    @POST("boss/deleteOriginMenu")
    fun deleteOriginMenu(@Field("originMenu_id") id: String): Call<ResponseBody>


    //홍보글 관리
    @FormUrlEncoded
    @POST("boss/createMenu")
    fun createMenu(@Field("originMenu_id") id: String,
                   @Field("discount") discount: Number,
                   @Field("quantity") quantity: Number,
                   @Field("method") method: String,
                   @Field("start_year") start_year: String,
                   @Field("start_month") start_month: String,
                   @Field("start_date") start_date: String,
                   @Field("start_hour") start_hour: String,
                   @Field("start_min") start_min: String,
                   @Field("end_year") end_year: String,
                   @Field("end_month") end_month: String,
                   @Field("end_date") end_date: String,
                   @Field("end_hour") end_hour: String,
                   @Field("end_min") end_min: String
                   ): Call<ResponseBody>

    @GET("/boss/getMenuList")
    fun getMenu(@Query("restaurant_id") id:String): Call<ResponseBody>

    //주문관리
    @GET("/boss/getPaidTicketList")
    fun getPaidTicketList(@Query("restaurant_id") id: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/boss/setTicketDisable")
    fun setTicketDisable(@Field("ticket_id") id: String): Call<ResponseBody>

    //판매내역관리
    @GET("/boss/getCertifiedTicketList")
    fun getCertifiedTicketList(@Query("restaurant_id") id: String): Call<ResponseBody>


}