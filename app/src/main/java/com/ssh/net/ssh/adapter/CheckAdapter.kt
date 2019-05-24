package com.ssh.net.ssh.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssh.net.ssh.R
import com.ssh.net.ssh.app.SSHApplication
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.viewHolder.CheckViewHolder

class CheckAdapter(val context: Context) : RecyclerView.Adapter<CheckViewHolder>() {

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
//        GlideHelper.loadInt(context as Activity, p0.mIvPic, R.drawable.img_five)
        GlideHelper.loadBitmapByCorner(context as Activity, p0.mIvPic, url)


    }
}