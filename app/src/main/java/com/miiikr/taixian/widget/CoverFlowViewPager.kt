package com.miiikr.taixian.widget

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.meng.viewpagercard_master.OnPageSelectListener
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.CoverFlowAdapter
import com.ssh.net.ssh.utils.ScreenUtils

import java.util.ArrayList

class CoverFlowViewPager(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), OnPageSelectListener {

    /**
     * 适配器
     */
    private var mAdapter: CoverFlowAdapter? = null

    /**
     * 用于左右滚动
     */
    private val mViewPager: ViewPager

    /**
     * 需要显示的视图集合
     */
    private val mViewList = ArrayList<View>()

    private var listener: OnPageSelectListener? = null

    init {
        View.inflate(context, R.layout.widget_cover_flow, this)
        mViewPager = findViewById<ViewPager>(R.id.vp_conver_flow)
        //        365dp 380
        val marginWidth = ScreenUtils.dip2px(getContext(), 120f)
        val displayWidth = ScreenUtils.getScreenWidth(getContext())
        val height = ScreenUtils.dip2px(getContext(), 450f)
        val layoutParams = RelativeLayout.LayoutParams(displayWidth - marginWidth, height)
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        mViewPager.layoutParams = layoutParams
        init()
    }

    /**
     * 初始化方法
     */
    private fun init() {
        // 构造适配器，传入数据源
        mAdapter = CoverFlowAdapter(mViewList, context)
        // 设置选中的回调
        mAdapter!!.setOnPageSelectListener(this)
        // 设置适配器
        mViewPager.adapter = mAdapter
        // 设置滑动的监听，因为adpter实现了滑动回调的接口，所以这里直接设置adpter
        mViewPager.addOnPageChangeListener(mAdapter!!)
        // 预加载个数
        mViewPager.offscreenPageLimit = 5

        // 设置触摸事件的分发
        setOnTouchListener { v, event ->
            // 传递给ViewPager 进行滑动处理
            mViewPager.dispatchTouchEvent(event)
        }

    }

    /**
     * 设置显示的数据，进行一层封装
     *
     * @param lists
     */
    fun setViewList(lists: List<View>?) {
        if (lists == null) {
            return
        }
        mViewList.clear()
        for (view in lists) {

            val layout = FrameLayout(context)
            // 设置padding 值，默认缩小
            layout.setPadding(CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding, CoverFlowAdapter.sWidthPadding, CoverFlowAdapter.sHeightPadding)
            layout.addView(view)
            mViewList.add(layout)
        }
        // 刷新数据
        mAdapter!!.notifyDataSetChanged()
    }


    /**
     * 当将某一个作为最中央时的回调
     *
     * @param listener
     */
    fun setOnPageSelectListener(listener: OnPageSelectListener) {
        this.listener = listener
    }


    // 显示的回调
    override fun select(position: Int) {
        if (listener != null) {
            listener!!.select(position)
        }
    }


}
