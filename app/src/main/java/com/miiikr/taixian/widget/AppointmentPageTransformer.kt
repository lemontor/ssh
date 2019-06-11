package com.miiikr.taixian.widget

import android.support.v4.view.ViewPager
import android.view.View

class AppointmentPageTransformer:ViewPager.PageTransformer {

    val MIN_SCALE = 0.9F

    override fun transformPage(view: View, position: Float) {
        var position = position
        /**
         * 过滤那些 <-1 或 >1 的值，使它区于【-1，1】之间
         */
        if (position < -1) {
            position = -1f
        } else if (position > 1) {
            position = 1f
        }
        /**
         * 判断是前一页 1 + position ，右滑 pos -> -1 变 0
         * 判断是后一页 1 - position ，左滑 pos -> 1 变 0
         */
        val tempScale = if (position < 0) 1 + position else 1 - position // [0,1]
        val scaleValue = MIN_SCALE + tempScale * 0.1f // [0,1]
        view.scaleX = scaleValue
        view.scaleY = scaleValue
    }
}