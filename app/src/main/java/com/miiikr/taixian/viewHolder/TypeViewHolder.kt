package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.miiikr.taixian.R

class TypeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
      var  mIvPic:ImageView

    init {
        mIvPic = itemView.findViewById(R.id.iv_pic)
    }

}