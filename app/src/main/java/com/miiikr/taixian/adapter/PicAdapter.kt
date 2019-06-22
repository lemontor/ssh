package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.bumptech.glide.Glide
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.PicEntity
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.viewHolder.PicViewHolder

class PicAdapter(val context: Context, var datas: ArrayList<PicEntity.PicData>, var onClickItemListener: OnPicListener) : RecyclerView.Adapter<PicViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PicViewHolder {
        return PicViewHolder(View.inflate(context, R.layout.item_pic, null))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: PicViewHolder, p1: Int) {
        val width = ScreenUtils.getPicWidthWithThreeCell(context)
        var lp = p0.mIvPic.layoutParams
        lp.height = width
        lp.width = width
        p0.mLayout.layoutParams = lp
        if (!TextUtils.isEmpty(datas[p1].img)) {
            GlideHelper.loadBitmapByNormalWithSize(context as Activity, p0.mIvPic, datas[p1].img!!,width,width)
            p0.mTvNotify.setBackgroundColor(context.resources.getColor(R.color.color_000000))
            p0.mIvCancel.visibility = View.VISIBLE
        } else {
//            p0.mIvPic.setImageResource(datas[p1].default)
            GlideHelper.loadInt(context as Activity,p0.mIvPic,datas[p1].default,width,width)
            p0.mTvNotify.setBackgroundColor(context.resources.getColor(R.color.color_888888))
            p0.mIvCancel.visibility = View.GONE
        }
        p0.mTvNotify.text = datas[p1].flag
        p0.mIvPic.setOnClickListener {
            onClickItemListener.clickItem(p1)
        }
        p0.mIvCancel.setOnClickListener {
            datas[p1].img = ""
            onClickItemListener.cancel(p1)
            notifyItemChanged(p1)
        }
    }




    interface OnPicListener {
        fun clickItem(position: Int)
        fun cancel(position: Int)
    }


}