package com.realmealboss.realmeal.Retrofit

import android.provider.ContactsContract
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null)

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
                  @Field("password") password: String): Call<ResponseDTO>

    @FormUrlEncoded
    @POST("boss/login")
    fun menuBoss(@Field("bossId") id: String,
                  @Field("password") password: String): Call<ResponseDTO>




}