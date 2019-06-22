package com.ssh.net.ssh.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.adapter.NormalAdapter
import com.miiikr.taixian.entity.ChoseEntity

class NormalPopupWindow(context: Context, type: Int, onClickItemListener: PopupClickListener, data: ArrayList<ChoseEntity>) : BasePopupWindow2(context, type, onClickItemListener,data) {

    lateinit var mIvDismiss: ImageView
    lateinit var mTvTitle: TextView
    lateinit var mTvNotify: TextView
    lateinit var mRvChose: RecyclerView

    override fun initData() {

    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int, onclickListener: PopupClickListener,datas:ArrayList<ChoseEntity>) {
        mIvDismiss = view.findViewById(R.id.iv_dismiss)
        mTvTitle = view.findViewById(R.id.tv_title)
        mTvNotify = view.findViewById(R.id.tv_notify)
        mRvChose = view.findViewById(R.id.rv_chose)
//        datas = ArrayList()
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvChose.layoutManager = linearLayoutManager
        mIvDismiss.setOnClickListener { dismiss() }
        when (type) {
            1 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_goods_func)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_func_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_func_basic), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_func_lost), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
            2 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_goods_material)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_material_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_material_golden), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_material_steel), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
            3 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_goods_style)
                mTvNotify.text = context.resources.getString(R.string.chose_goods_style_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_man), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_women), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
            4 -> {
                mTvTitle.text = context.resources.getString(R.string.chose_bag_size)
                mTvNotify.text = context.resources.getString(R.string.bag_size_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_mini), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_big), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_medium), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_small), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
            5 -> {
                mTvTitle.text = context.resources.getString(R.string.jewelry_title)
                mTvNotify.text = context.resources.getString(R.string.jewelry_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_org_diamond), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_last_diamond), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
            6 -> {
                mTvTitle.text = context.resources.getString(R.string.jewelry_material)
                mTvNotify.text = context.resources.getString(R.string.jewelry_material_notify)
//                datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_pt950), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_au750), false))
//                datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_925), false))
                mRvChose.adapter = NormalAdapter(context, datas, onclickListener, type)
            }
        }
    }

    override fun getContentViews(): Int {
        return R.layout.layout_normal_popupwindow
    }
}