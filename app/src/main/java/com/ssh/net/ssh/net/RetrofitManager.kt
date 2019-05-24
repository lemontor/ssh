package com.ssh.net.ssh.net

import com.ssh.net.ssh.utils.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager {

    var mRetrofit: Retrofit? = null

    fun initRetrofit(): Retrofit? {
       val baseUrl =  if(AppConfig.isDeBug)  AppConfig.debugUrl else AppConfig.lineUrl
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
                .build()
        return okHttpClient
    }

}