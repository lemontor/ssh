package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CheckEntity
import com.miiikr.taixian.entity.SellEntity
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.SpannableUtils
import com.ssh.net.ssh.viewHolder.CheckViewHolder

class SellAdapter(val context: Context, val datas:ArrayList<SellEntity.SellDataEntity>, val onClickItemListener: OnClickItemListener) : RecyclerView.Adapter<CheckViewHolder>() {

    var mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CheckViewHolder {
        return CheckViewHolder(mLayoutInflater.inflate(R.layout.item_check, null))
    }

    override fun getItemCount(): Int {
        if(datas == null || datas.size ==0){
            return 0
        }
        return datas.size
    }

    override fun onBindViewHolder(p0: CheckViewHolder, p1: Int) {

        GlideHelper.loadBitmapByCornerWithPlaceholder(context as Activity, p0.mIvPic, datas[p1].img,R.mipmap.icon_empty_pic)
        p0.mTvType.text = datas[p1].brandName
        p0.mTvDate.text = SpannableUtils.millisecond2Date(datas[p1].createTime)
        when(datas[p1].state){
            0 -> p0.mTvState.text = "#进行中"
            1 -> p0.mTvState.text = "#完成"
            2 -> p0.mTvState.text = "#取消"
        }
        when(datas[p1].recoveryType){
            1->p0.mTvFlag.text = "#上门回收"
            2->p0.mTvFlag.text = "#到店回收"
            3->p0.mTvFlag.text = "#快递回收"
        }
        p0.mLayout.setOnClickListener {
            onClickItemListener.clickItem(p1)
        }
    }


}