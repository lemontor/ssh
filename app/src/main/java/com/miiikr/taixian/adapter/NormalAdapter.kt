package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.entity.ChoseEntity
import com.ssh.net.ssh.viewHolder.NormalViewHolder

class NormalAdapter(private var context: Context, private var datas: ArrayList<ChoseEntity>, val onClickItemListener: PopupClickListener,val type:Int) : RecyclerView.Adapter<NormalViewHolder>() {

    var clickIndex = -1
    var beforeViewHolder:NormalViewHolder?=null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NormalViewHolder {
        return NormalViewHolder(View.inflate(context, R.layout.item_chose, null))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: NormalViewHolder, p1: Int) {
        p0.mCbChose.isChecked = datas[p1].isCheck
        clickIndex = if(datas[p1].isCheck) p1 else -1
        p0.mTvTitle.text = datas[p1].flag
        p0.mCbChose.setOnClickListener {
            if (clickIndex != -1) {
                if (clickIndex == p1) {
                    onClickItemListener.onClick(-1,type,"")
                    datas[clickIndex].isCheck = false
                    beforeViewHolder!!.mCbChose.isChecked = false
                    clickIndex = -1
                } else {
                    datas[clickIndex].isCheck = false
                    beforeViewHolder!!.mCbChose.isChecked = false
                    datas[p1].isCheck = true
                    p0.mCbChose.isChecked = true
                    clickIndex = p1
                    beforeViewHolder = p0
                    onClickItemListener.onClick(p1,type,datas[p1].flag)
                }
            } else {
                datas[p1].isCheck = true
                p0.mCbChose.isChecked = true
                clickIndex = p1
                beforeViewHolder = p0
                onClickItemListener.onClick(p1,type,datas[p1].flag)
            }
        }
    }
}