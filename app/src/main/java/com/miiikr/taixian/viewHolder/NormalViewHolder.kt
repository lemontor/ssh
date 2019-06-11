package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.R

class NormalViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var mTvTitle:TextView
    var mCbChose:CheckBox

    init {
        mTvTitle = itemView.findViewById(R.id.tv_title)
        mCbChose = itemView.findViewById(R.id.cb_chose)
    }

}