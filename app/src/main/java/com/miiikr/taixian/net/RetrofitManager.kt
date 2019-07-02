package com.miiikr.taixian.net

import com.ssh.net.ssh.utils.AppConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    var mRetrofit: Retrofit? = null


    fun initRetrofit(): Retrofit? {
       val baseUrl =  if(AppConfig.isDeBug)  AppConfig.debugUrl else AppConfig.lineUrl
       mRetrofit =  Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(NullOnEmptyConverterFactory())
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .client(initOkHttpClient())
               .build()
        return mRetrofit
    }

    private fun initOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(AddCookiesInterceptor())
                .addInterceptor(ReceivedCookiesInterceptor())
                .build()
        return okHttpClient
    }

}