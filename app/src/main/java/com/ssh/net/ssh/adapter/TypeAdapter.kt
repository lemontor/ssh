package com.ssh.net.ssh.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.viewHolder.TypeViewHolder

class TypeAdapter(val context: Context):RecyclerView.Adapter<TypeViewHolder>() {

    val layoutInflater:LayoutInflater

    init {
       layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TypeViewHolder {
        return TypeViewHolder(layoutInflater.inflate(R.layout.item_type,p0,false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(p0: TypeViewHolder, p1: Int) {
        p0.mIvPic.setImageResource(R.mipmap.test)
        p0.mIvPic.setOnClickListener {
            IntentUtils.toThing(context)
        }
    }
}