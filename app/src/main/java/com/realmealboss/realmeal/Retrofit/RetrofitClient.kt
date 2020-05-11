package com.realmealboss.realmeal.Retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var instance: Retrofit?=null

    fun getInstance():Retrofit{
        if(instance == null)
            instance = Retrofit.Builder()
                .baseUrl("http://192.168.25.30:3000") // 주소값 바꿔주기
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return instance!!
    }
}