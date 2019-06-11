package com.ssh.net.ssh.widget

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.R

class FuncPopupWindow(mContext: Context, var type: Int) : BasePopupWindow(mContext,type) {

    lateinit var mIvDismiss: ImageView
    lateinit var mCbNormal: CheckBox
    lateinit var mCbLost: CheckBox
    lateinit var mTvTitle: TextView
    lateinit var mTvNotify: TextView
    lateinit var mTvTypeOne: TextView
    lateinit var mTvTypeTwo: TextView


    var onChoseFuncListener: OnChoseFuncListener? = null

    interface OnChoseFuncListener {
        fun choseFuncNormal()
        fun choseFuncLost()
    }

    fun setOnFuncListener(listener: OnChoseFuncListener) {
        this.onChoseFuncListener = listener
    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_func
    }

    override fun initData() {
    }

    override fun initListener(context:Context) {
        mIvDismiss.setOnClickListener {
            if(isShowing){
                dismiss()
            }
        }
    }

    override fun initView(context:Context,view: View,mType:Int) {
        mTvTitle = view.findViewById(R.id.tv_title)
        mIvDismiss = view.findViewById(R.id.iv_dismiss)
        mCbNormal = view.findViewById(R.id.cb_sure)
        mCbLost = view.findViewById(R.id.cb_lost)
        mTvNotify = view.findViewById(R.id.tv_notify)
        mTvTypeOne = view.findViewById(R.id.tv_func_basic)
        mTvTypeTwo = view.findViewById(R.id.tv_func_lost)
        when (mType) {
            0 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_goods_func)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_func_notify)
                mTvTypeOne.text = context.resources.getString(R.string.chose_goods_func_basic)
                mTvTypeTwo.text = context.resources.getString(R.string.chose_goods_func_lost)
            }
            1 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_goods_material)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_material_notify)
                mTvTypeOne.text = context.resources.getString(R.string.chose_goods_material_golden)
                mTvTypeTwo.text = context.resources.getString(R.string.chose_goods_material_steel)
            }
            2->{
                mTvTitle.text = context.resources.getString(R.string.chose_goods_style)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_style_notify)
                mTvTypeOne.text = context.resources.getString(R.string.chose_goods_style_man)
                mTvTypeTwo.text = context.resources.getString(R.string.chose_goods_style_women)
            }
            else ->{}
        }

    }


}