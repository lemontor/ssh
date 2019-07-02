package com.miiikr.taixian.utils

import android.content.Context
import android.graphics.Rect
import android.view.*

import java.lang.reflect.Field

object AndroidWorkaround{


     fun getNavigationBarHeight(context: Context): Int {
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        if (!hasMenuKey && !hasBackKey) {
            val resources = context.resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            //获取NavigationBar的高度
            return resources.getDimensionPixelSize(resourceId)
        } else {
            return 0
        }
    }





}
