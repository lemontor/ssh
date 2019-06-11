package com.miiikr.taixian.net

import android.content.Context
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.utils.SharedPreferenceUtils
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor():Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if(!response.headers("Set-Cookie").isEmpty()){
            var cookies = HashSet<String>()
            for (header in response.headers("Set-Cookie")) {
                cookies.add(header)
            }
            SharedPreferenceUtils(SSHApplication.instance.applicationContext).putStringSet(SharedPreferenceUtils.PREFERENCE_COOKIES_NAME,cookies)
        }
        return response
    }

}