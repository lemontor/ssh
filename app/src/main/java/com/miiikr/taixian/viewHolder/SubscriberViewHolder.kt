package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.R

class SubscriberViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var mTvDate:TextView
    var mTvName:TextView
    var mTvLevel:TextView
    var mTvSubTime:TextView
    var mTvConusult:TextView
    var mTvCancel:TextView
    var mIvPic:ImageView
    var mTitleNotify:TextView?=null
    var mViewLineA:View
    var mViewLineB:View
    var mViewLineC:View


    init {
        mTvDate = itemView.findViewById(R.id.tv_date)
        mTvName = itemView.findViewById(R.id.tv_name)
        mTvLevel = itemView.findViewById(R.id.tv_level)
        mTvSubTime = itemView.findViewById(R.id.tv_sub_time)
        mTvConusult = itemView.findViewById(R.id.tv_consult)
        mTvCancel = itemView.findViewById(R.id.tv_sub_cancel)
        mIvPic = itemView.findViewById(R.id.iv_head)
        mViewLineA = itemView.findViewById(R.id.line)
        mViewLineB = itemView.findViewById(R.id.line_b)
        mViewLineC = itemView.findViewById(R.id.line_c)

    }


    fun getTv(res:Int):TextView?{
        if(mTitleNotify == null){
            mTitleNotify = itemView.findViewById(res)
        }
        return mTitleNotify
    }



}