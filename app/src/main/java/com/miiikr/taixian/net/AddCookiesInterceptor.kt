package com.miiikr.taixian.net

import android.content.Context
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.utils.SharedPreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = SharedPreferenceUtils(SSHApplication.instance.applicationContext).getCookies(SharedPreferenceUtils.PREFERENCE_COOKIES_NAME)
        if(preferences == null){
            return chain.proceed(builder.build())
        }
        for (cookie in preferences!!) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}