package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.SubEntity
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.SpannableUtils
import com.ssh.net.ssh.viewHolder.SubscriberViewHolder

class SubscribeAdapter(val context: Context, val datas: ArrayList<SubEntity.SubDataEntity>) : RecyclerView.Adapter<SubscriberViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubscriberViewHolder {
        return SubscriberViewHolder(inflater.inflate(R.layout.item_subscribe, null))
    }

    override fun getItemCount(): Int {
        if (datas == null || datas.size == 0) {
            return 0
        }
        return datas.size
    }

    override fun onBindViewHolder(p0: SubscriberViewHolder, p1: Int) {
        if (datas[p1].recoveryTypeId == 1) {//上门服务
            p0.mIvPic.visibility = View.VISIBLE
            p0.mTvName.visibility = View.VISIBLE
            p0.mTvLevel.visibility = View.VISIBLE
            p0.getTv(R.id.tv_title_notify)!!.visibility = View.INVISIBLE
            p0.mTvCancel.visibility = View.VISIBLE
            p0.mTvConusult.visibility = View.VISIBLE
            p0.mViewLineA.visibility = View.VISIBLE
            p0.mViewLineB.visibility = View.VISIBLE
            p0.mViewLineC.visibility = View.VISIBLE
            p0.mTvSubTime.visibility = View.VISIBLE
            p0.mTvDate.text = SpannableUtils.millisecond2Date(datas[p1].createTime)
            if (datas[p1].gemmologistInfo != null) {
                if (datas[p1].gemmologistInfo!!.headPortrait != null) {
                    GlideHelper.loadBitmapByCornerWithPlaceholder(context as Activity, p0.mIvPic, datas[p1].gemmologistInfo!!.headPortrait, R.mipmap.icon_pic_women)
                }
                if (datas[p1].gemmologistInfo!!.gemmologistName != null) {
                    p0.mTvName.text = datas[p1].gemmologistInfo!!.gemmologistName
                }
                if (datas[p1].gemmologistInfo!!.level != null) {
                    p0.mTvLevel.text = datas[p1].gemmologistInfo!!.level
                }
                if (datas[p1].reservationTime != null) {
                    val d = SpannableUtils.millisecond2Date(datas[p1].reservationTime)
                    val s = p0.mTvSubTime.text.toString()
                    p0.mTvSubTime.text = "$s$d"
                }
            }
        } else if (datas[p1].recoveryTypeId == 2) {//服务网点回收
            p0.mIvPic.visibility = View.INVISIBLE
            p0.mTvName.visibility = View.INVISIBLE
            p0.mTvLevel.visibility = View.INVISIBLE
            p0.getTv(R.id.tv_title_notify)!!.visibility = View.VISIBLE
            p0.getTv(R.id.tv_title_notify)!!.text = "#服务网点回收"
            p0.mTvCancel.visibility = View.VISIBLE
            p0.mTvConusult.visibility = View.VISIBLE
            p0.mViewLineA.visibility = View.VISIBLE
            p0.mViewLineB.visibility = View.VISIBLE
            p0.mViewLineC.visibility = View.VISIBLE
            p0.mTvSubTime.visibility = View.VISIBLE
            p0.mTvDate.text = SpannableUtils.millisecond2Date(datas[p1].createTime)
            if (datas[p1].reservationTime != null) {
                val d = SpannableUtils.millisecond2Date(datas[p1].reservationTime)
                val s = p0.mTvSubTime.text.toString()
                p0.mTvSubTime.text = "$s$d"
            }
        } else if (datas[p1].recoveryTypeId == 3) {//快递回收
            p0.mIvPic.visibility = View.INVISIBLE
            p0.mTvName.visibility = View.INVISIBLE
            p0.mTvLevel.visibility = View.INVISIBLE
            p0.getTv(R.id.tv_title_notify)!!.visibility = View.VISIBLE
            p0.getTv(R.id.tv_title_notify)!!.text = "#顺丰速运"
            p0.mTvCancel.visibility = View.INVISIBLE
            p0.mTvConusult.visibility = View.INVISIBLE
            p0.mViewLineA.visibility = View.INVISIBLE
            p0.mViewLineB.visibility = View.INVISIBLE
            p0.mViewLineC.visibility = View.INVISIBLE
            p0.mTvSubTime.visibility = View.INVISIBLE
            p0.mTvDate.text = SpannableUtils.millisecond2Date(datas[p1].createTime)
        }


//        GlideHelper.loadBitmpaByCircle(context as Activity, p0.mIvPic, url)
    }
}