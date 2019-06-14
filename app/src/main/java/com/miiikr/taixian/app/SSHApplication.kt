package com.miiikr.taixian.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.SparseArray
import com.miiikr.taixian.`interface`.MemoryListenerInterface
import com.squareup.leakcanary.LeakCanary
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import okhttp3.internal.Internal.instance
import kotlin.properties.Delegates

class SSHApplication : Application() {

    var api: IWXAPI? = null

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        registerComponentCallbacks(MemoryListenerInterface())
        LeakCanary.install(this)
        instance = this
        regToWx()
    }

    fun regToWx(){
        api = WXAPIFactory.createWXAPI(this,appId,true)
        api!!.registerApp(appId)

    }


    companion object {
        val appId = "wx803aac2b6c966257"

        var instance: SSHApplication by Delegates.notNull()

        var index = 0

        var activitys: SparseArray<Activity> = SparseArray()

        fun addActivity(activity: Activity) {
            activitys.put(index, activity)
            index++
        }

        fun clearAll() {
            for (i in 0..activitys.size() - 1) {
                val ac = activitys.valueAt(i)
                ac.finish()
            }
        }
    }


}