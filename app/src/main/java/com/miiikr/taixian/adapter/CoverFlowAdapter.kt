package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup

import com.meng.viewpagercard_master.OnPageSelectListener
import com.miiikr.taixian.widget.CoverFlowViewPager
import com.ssh.net.ssh.utils.ScreenUtils

class CoverFlowAdapter(
        /**
         * 子元素的集合
         */
        private val mViewList: List<View>?,
        /**
         * 上下文对象
         */
        private val mContext: Context) : PagerAdapter(), ViewPager.OnPageChangeListener {

    /**
     * 滑动监听的回调接口
     */
    private var listener: OnPageSelectListener? = null

    init {
        // 设置padding值
        sWidthPadding = ScreenUtils.dip2px(mContext, 16f)
        sHeightPadding = ScreenUtils.dip2px(mContext, 28f)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViewList!![position])
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = mViewList!![position]
        container.addView(view)

        return view
    }

    override fun getCount(): Int {
        return mViewList?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // 该方法回调ViewPager 的滑动偏移量
        if (mViewList!!.size > 0 && position < mViewList.size) {
            //当前手指触摸滑动的页面,从0页滑动到1页 offset越来越大，padding越来越大
            val outHeightPadding = (positionOffset * sHeightPadding).toInt()
            val outWidthPadding = (positionOffset * sWidthPadding).toInt()
            // 从0滑动到一时，此时position = 0，其应该是缩小的，符合
            mViewList[position].setPadding(outWidthPadding, outHeightPadding, outWidthPadding, outHeightPadding)

            // position+1 为即将显示的页面，越来越大
            if (position < mViewList.size - 1) {
                val inWidthPadding = ((1 - positionOffset) * sWidthPadding).toInt()
                val inHeightPadding = ((1 - positionOffset) * sHeightPadding).toInt()
                mViewList[position + 1].setPadding(inWidthPadding, inHeightPadding, inWidthPadding, inHeightPadding)
            }
        }

    }

    override fun onPageSelected(position: Int) {
        // 回调选择的接口
        if (listener != null) {
            listener!!.select(position)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    fun setOnPageSelectListener(listener: CoverFlowViewPager) {
        this.listener = listener
    }

    companion object {

        /**
         * 默认缩小的padding值
         */
        var sWidthPadding: Int = 0

        var sHeightPadding: Int = 0
    }

}
