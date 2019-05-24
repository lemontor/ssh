package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ssh.net.ssh.R

class CheckViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var mIvPic:ImageView
    var mTvDate:TextView
    var mTvState:TextView
    var mTvFlag:TextView
    var mTvNotify:TextView

    init {
        mIvPic = itemView.findViewById(R.id.iv_pic)
        mTvDate = itemView.findViewById(R.id.tv_date)
        mTvState = itemView.findViewById(R.id.tv_state)
        mTvFlag = itemView.findViewById(R.id.tv_flag)
        mTvNotify = itemView.findViewById(R.id.tv_notify)

    }





}