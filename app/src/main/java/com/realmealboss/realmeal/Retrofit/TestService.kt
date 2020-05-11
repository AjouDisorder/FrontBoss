package com.realmealboss.realmeal.Retrofit

import retrofit2.Call
import retrofit2.http.*

data class ResponseDTOex(var result:String? = null)

interface TestService {

    @GET("/rtest")
    fun getRequest(@Query("name") name:String): Call<ResponseDTOex>

    @GET("/rtest/{id}")
    fun getParamRequest(@Query("id") id:String): Call<ResponseDTOex>
    //formData
    //UrlEncoded
    @FormUrlEncoded
    @POST("/rtest")
    fun postRequest(@Field("id") id:String,
                    @Field("password") password:String): Call<ResponseDTOex>

    @FormUrlEncoded
    @PUT("/rtest/{id}")
    fun putRequest(@Path("id") id:String,
                    @Field("password") password:String): Call<ResponseDTOex>

    @DELETE("rtest/{id}")
    fun deleteRequest(@Path("id") id: String): Call<ResponseDTOex>

}