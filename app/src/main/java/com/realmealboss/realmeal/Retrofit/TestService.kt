package com.realmealboss.realmeal.Retrofit

import retrofit2.Call
import retrofit2.http.*

data class ResponseDTO(var result:String? = null)

interface TestService {

    @GET("/rtest")
    fun getRequest(@Query("name") name:String): Call<ResponseDTO>

    @GET("/rtest/{id}")
    fun getParamRequest(@Query("id") id:String): Call<ResponseDTO>
    //formData
    //UrlEncoded
    @FormUrlEncoded
    @POST("/rtest")
    fun postRequest(@Field("id") id:String,
                    @Field("password") password:String): Call<ResponseDTO>

    @FormUrlEncoded
    @PUT("/rtest/{id}")
    fun putRequest(@Path("id") id:String,
                    @Field("password") password:String): Call<ResponseDTO>

    @DELETE("rtest/{id}")
    fun deleteRequest(@Path("id") id: String): Call<ResponseDTO>

}