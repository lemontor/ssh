package com.ssh.net.ssh.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_brand.view.*
import java.lang.Exception


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

        fun assistActivity(content: View) {
            androidWorkAround(content)
        }

        private var mChildOfContent: View? = null
        private var usableHeightPrevious: Int = 0
        private var frameLayoutParams: ViewGroup.LayoutParams? = null

        fun  androidWorkAround(content: View){
            mChildOfContent = content
            mChildOfContent!!.viewTreeObserver.addOnGlobalLayoutListener {
                possiblyResizeChildOfContent()
            }
            frameLayoutParams = mChildOfContent!!.layoutParams
        }

        fun possiblyResizeChildOfContent(){
            val usableHeightNow = computeUsableHeight()
            if (usableHeightNow != usableHeightPrevious) {
                frameLayoutParams!!.height = usableHeightNow
                mChildOfContent!!.requestLayout()
                usableHeightPrevious = usableHeightNow
            }
        }

        fun computeUsableHeight():Int{
            val r = Rect()
            mChildOfContent!!.getWindowVisibleDisplayFrame(r)
            return (r.bottom)
        }

        fun checkDeviceHasNavigationBar(context:Context):Boolean {
            var hasNavigationBar = false
            val rs = context.resources
            val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass = Class.forName("android.os.SystemProperties")
                val m = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1".equals(navBarOverride)) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            }catch (e:Exception) {

            }
            return hasNavigationBar;
        }
    }


}