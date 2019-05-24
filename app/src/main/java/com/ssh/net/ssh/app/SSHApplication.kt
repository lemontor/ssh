package com.ssh.net.ssh.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.SparseArray

class SSHApplication : Application() {


    override fun onCreate() {
        super.onCreate()
    }

    companion object {

        var index = 0

        var activitys: SparseArray<Activity> = SparseArray()

        fun  addActivity(activity: Activity){
            activitys.put(index,activity)
            index++
        }

        fun clearAll(){
            for(i in 0..activitys.size()-1){
                val ac = activitys.valueAt(i)
                ac.finish()
            }
        }
    }


}