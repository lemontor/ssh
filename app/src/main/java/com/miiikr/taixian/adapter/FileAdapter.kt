package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import ccom.miiikr.taixian.entity.FlagEntity
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.ChoseEntity
import com.ssh.net.ssh.viewHolder.FileViewHolder

class FileAdapter(val context: Context, val datas: ArrayList<ChoseEntity>, var onClickItemListener: OnClickItemListener) : RecyclerView.Adapter<FileViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FileViewHolder {
        return FileViewHolder(View.inflate(context, R.layout.item_file, null))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: FileViewHolder, p1: Int) {
        if (datas[p1].isCheck) {
            p0.tvFlag.setBackgroundResource(R.drawable.bg_file_check)
            p0.tvFlag.setTextColor(context.resources.getColor(R.color.color_ffffff))
        } else {
            p0.tvFlag.setBackgroundResource(R.drawable.bg_file_uncheck)
            p0.tvFlag.setTextColor(context.resources.getColor(R.color.color_888888))
        }
        p0.tvFlag.text = datas[p1].flag
        p0.tvFlag.setOnClickListener { onClickItemListener.clickItem(p1) }
    }
}