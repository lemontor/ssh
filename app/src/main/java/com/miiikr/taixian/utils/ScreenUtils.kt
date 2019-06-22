package com.ssh.net.ssh.utils

import android.content.Context
import android.util.Log
import android.util.TypedValue

class ScreenUtils {

    companion object {

        /**
         * 获取状态栏高度
         *
         * @param context context
         * @return 状态栏高度
         */
        fun getStatusBarHeight(context: Context): Int {
            // 获得状态栏高度
            val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        }

        fun getScreenWidth(context: Context): Int {
            val resources = context.resources
            val dm = resources.displayMetrics
            return dm.widthPixels
        }

        fun getScreenHeight(context: Context): Int {
            val resources = context.resources
            val dm = resources.displayMetrics
            return dm.heightPixels
        }


        fun dp2px(context: Context, dpValue: Int): Int {
            var scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun dpToPx(context: Context, valueInDp: Float): Float {
            val metrics = context.resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
        }

        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun getPicWidthWithThreeCell(context: Context): Int {
            val screenWidth = getScreenWidth(context)
            Log.e("tag_width", "" + screenWidth)
            val margin = dp2px(context, 20) * 2
            Log.e("tag_margin", "" + margin)
            val center = dp2px(context, 6) * 3
//            Log.e("tag_center", "" + center)
            val marginLeftAndRight = margin + center
//            val marginLeftAndRight = margin
            Log.e("tag_marginLeftAndRight", "" + marginLeftAndRight)
            return (screenWidth - marginLeftAndRight) / 3
        }


    }



}