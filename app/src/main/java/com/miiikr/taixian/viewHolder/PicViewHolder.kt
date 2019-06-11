package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.miiikr.taixian.R

class PicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var mTvNotify: TextView
    var mIvPic: ImageView
    var mIvCancel: ImageView
    var mLayout: RelativeLayout

    init {
        mTvNotify = itemView.findViewById(R.id.tv_pic_notify)
        mIvCancel = itemView.findViewById(R.id.iv_cancel)
        mIvPic = itemView.findViewById(R.id.iv_pic)
        mLayout = itemView.findViewById(R.id.layout_pic)
    }

}