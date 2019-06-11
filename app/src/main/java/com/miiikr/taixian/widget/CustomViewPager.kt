package com.miiikr.taixian.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

class CustomViewPager : ViewPager {

    constructor(context: Context) : super(context)


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val childCount = childCount
        var h = 0
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            child.measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED))
            val measureHeight = child.measuredHeight
            if(measureHeight > h){
                h = measureHeight
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(h,MeasureSpec.EXACTLY)  )
    }


}