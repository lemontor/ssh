package com.miiikr.taixian.net

import com.ssh.net.ssh.utils.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager2 {

    var mRetrofit: Retrofit? = null


    fun initRetrofit(): Retrofit? {
       val baseUrl =  "http://192.168.101.160:8080/"
       mRetrofit =  Retrofit.Builder()
               .baseUrl(baseUrl)
               .client(initOkHttpClient())
               .addConverterFactory(GsonConverterFactory.create())
               .build()
        return mRetrofit
    }

    private fun initOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(AddCookiesInterceptor()) //这部分
                .addInterceptor(ReceivedCookiesInterceptor()) //这部分
                .build()
        return okHttpClient
    }

}