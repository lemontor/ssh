package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.ChoseEntity
import com.ssh.net.ssh.viewHolder.NormalViewHolder

class NormalAdapter(private var context: Context,private var datas: ArrayList<ChoseEntity>) : RecyclerView.Adapter<NormalViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NormalViewHolder {
        return NormalViewHolder(View.inflate(context, R.layout.item_chose, null))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: NormalViewHolder, p1: Int) {
        p0.mCbChose.isChecked = datas[p1].isCheck
        p0.mTvTitle.text = datas[p1].flag
    }
}