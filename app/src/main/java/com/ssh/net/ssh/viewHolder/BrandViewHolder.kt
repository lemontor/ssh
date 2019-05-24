package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ssh.net.ssh.R

class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var layout: LinearLayout
    var mIvBrand: ImageView
    var mTvIndex:TextView

    init {
        layout = itemView.findViewById(R.id.layout)
        mIvBrand = itemView.findViewById(R.id.iv_brand)
        mTvIndex = itemView.findViewById(R.id.tv_index)
    }
}