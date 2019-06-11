package com.miiikr.taixian.`interface`

import android.content.ComponentCallbacks2
import android.content.ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN
import android.content.res.Configuration
import com.bumptech.glide.Glide
import com.miiikr.taixian.app.SSHApplication

class MemoryListenerInterface:ComponentCallbacks2 {

    override fun onLowMemory() {
        Glide.get(SSHApplication.instance.applicationContext).clearMemory()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {}

    override fun onTrimMemory(level: Int) {
        if(level == TRIM_MEMORY_UI_HIDDEN){
            Glide.get(SSHApplication.instance.applicationContext).clearMemory()
        }
        Glide.get(SSHApplication.instance.applicationContext).trimMemory(level)
    }
}