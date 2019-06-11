package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.viewHolder.SubscriberViewHolder

class SubscribeAdapter(val context: Context) : RecyclerView.Adapter<SubscriberViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SubscriberViewHolder {
        return SubscriberViewHolder(inflater.inflate(R.layout.item_subscribe, null))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: SubscriberViewHolder, p1: Int) {
        val url = "http://img.duoziwang.com/2016/07/27/2139407201.jpg"
        GlideHelper.loadBitmpaByCircle(context as Activity, p0.mIvPic, url)
    }
}