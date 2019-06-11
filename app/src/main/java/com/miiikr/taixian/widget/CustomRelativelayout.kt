package com.miiikr.taixian.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class CustomRelativelayout : RelativeLayout {


    constructor(context: Context) : super(context)


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec))
        var childWidthSize = measuredWidth
        var widthMeasure = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY)
        var heightMeasure = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasure, heightMeasure)
    }


}