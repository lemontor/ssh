package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.viewHolder.CheckViewHolder

class CheckAdapter(val context: Context,val onClickItemListener: OnClickItemListener) : RecyclerView.Adapter<CheckViewHolder>() {

    var mLayoutInflater: LayoutInflater

    init {
        mLayoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CheckViewHolder {
        return CheckViewHolder(mLayoutInflater.inflate(R.layout.item_check, null))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: CheckViewHolder, p1: Int) {
        val url = "http://img.duoziwang.com/2016/07/27/2139407201.jpg"
        GlideHelper.loadBitmapByCorner(context as Activity, p0.mIvPic, url)
        p0.mLayout.setOnClickListener {
            onClickItemListener.clickItem(p1)
        }
    }


}