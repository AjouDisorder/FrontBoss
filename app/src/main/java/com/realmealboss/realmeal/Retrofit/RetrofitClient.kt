package com.realmealboss.realmeal.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var instance: Retrofit?=null

    fun getInstance():Retrofit{
        if(instance == null)
            instance = Retrofit.Builder()
                .baseUrl("http://192.168.0.35:3000") // 주소값 바꿔주기
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return instance!!
    }
}