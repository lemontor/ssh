package com.ssh.net.ssh.widget

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.entity.ChoseEntity


/**
 * Created by sand on 2018/3/30.
 */

abstract class BasePopupWindow2 : PopupWindow, View.OnClickListener {
    /**
     * 上下文
     */
    private var context: Context? = null
    /**
     * 最上边的背景视图
     */
    private var vBgBasePicker: View? = null
    /**
     * 内容viewgroup
     */
    private var llBaseContentPicker: LinearLayout? = null
    /**
     * 初始化布局
     *
     * @return
     */


    constructor(context: Context,type: Int,clickItemListener: PopupClickListener,data:ArrayList<ChoseEntity>) : super(context) {
        this.context = context
        val view = View.inflate(context, R.layout.base_popupwindow_layou, null)
        vBgBasePicker = view.findViewById(R.id.v_bg_base_picker)
        llBaseContentPicker = view.findViewById<View>(R.id.ll_base_content_picker) as LinearLayout



        /***
         * 添加布局到界面中
         */
        llBaseContentPicker!!.addView(View.inflate(context, getContentViews(), null))
        contentView = view
        //设置PopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置PopupWindow弹出窗体的高
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true//设置获取焦点
        isTouchable = true//设置可以触摸
        isOutsideTouchable = true//设置外边可以点击
        val dw = ColorDrawable(0xffffff)
        setBackgroundDrawable(dw)
        // 设置背景颜色变暗
        val lp = (context as Activity).window
                .attributes
        lp.alpha = 0.4f
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        context.window.attributes = lp
        //消失的时候设置窗体背景变亮
        setOnDismissListener {
            val lp = context.window.attributes
            lp.alpha = 1.0f
            context.window.attributes = lp
        }
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.BottomDialogWindowAnim
        initView(context,view,type,clickItemListener,data)
        initListener(context)
        initData()
        vBgBasePicker!!.setOnClickListener(this)
    }

    constructor(context: Context, isShowBackGround: Boolean, h: Int,type: Int,clickItemListener: PopupClickListener,datas:ArrayList<ChoseEntity>) : super(context) {
        this.context = context
        val view = View.inflate(context, R.layout.base_popupwindow_layou, null)
        vBgBasePicker = view.findViewById(R.id.v_bg_base_picker)
        llBaseContentPicker = view.findViewById<View>(R.id.ll_base_content_picker) as LinearLayout
        /***
         * 添加布局到界面中
         */
        llBaseContentPicker!!.addView(View.inflate(context, getContentViews(), null))
        contentView = view
        //设置PopupWindow弹出窗体的宽
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置PopupWindow弹出窗体的高
        this.height = h
        isFocusable = true//设置获取焦点
        isTouchable = true//设置可以触摸
        isOutsideTouchable = true//设置外边可以点击

        val dw = ColorDrawable(0xffffff)
        setBackgroundDrawable(dw)

        if (isShowBackGround) {
            //设置PopupWindow弹出窗体的高
            this.height = h

            // 设置背景颜色变暗
            val lp = (context as Activity).window
                    .attributes
            lp.alpha = 0.4f
            context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            context.window.attributes = lp
            //消失的时候设置窗体背景变亮
            setOnDismissListener {
                val lp = context.window.attributes
                lp.alpha = 1.0f
                context.window.attributes = lp
            }
        }

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.BottomDialogWindowAnim
        initView(context,view,type,clickItemListener,datas)
        initListener(context)
        initData()
        vBgBasePicker!!.setOnClickListener(this)
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化监听
     */
    protected abstract fun initListener(context: Context)

    /**
     * 初始化view
     *
     * @param view
     */
    protected abstract fun initView(context: Context,view: View,type:Int,clickItemListener: PopupClickListener,data: ArrayList<ChoseEntity>)

    protected abstract fun getContentViews():Int



    /**
     * 为了适配7.0系统以上显示问题(显示在控件的底部)
     *
     * @param anchor
     */
    override fun showAsDropDown(anchor: View) {
        if (Build.VERSION.SDK_INT >= 24) {
            val rect = Rect()
            anchor.getGlobalVisibleRect(rect)
            val h = anchor.resources.displayMetrics.heightPixels - rect.bottom
            height = h
        }
        super.showAsDropDown(anchor)
    }

    /**
     * 展示在屏幕的底部
     *
     * @param layoutid rootview
     */
    fun showAtLocation(@LayoutRes layoutid: Int) {
        vBgBasePicker!!.visibility = View.VISIBLE
        showAtLocation(LayoutInflater.from(context).inflate(layoutid, null),
                Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    /**
     * 展示在屏幕的底部
     *
     * @param layoutid rootview
     */
    fun showAtLocationBottom(@LayoutRes layoutid: Int) {
        vBgBasePicker!!.visibility = View.VISIBLE
        showAtLocation(LayoutInflater.from(context).inflate(layoutid, null),
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 0)
    }

    /**
     * 最上边视图的点击事件的监听
     *
     * @param v
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.v_bg_base_picker -> {
                vBgBasePicker!!.visibility = View.GONE
                dismiss()
            }
        }
    }
}
