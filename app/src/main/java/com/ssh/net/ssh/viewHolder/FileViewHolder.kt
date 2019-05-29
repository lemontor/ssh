package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ssh.net.ssh.R

class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var tvFlag: TextView

    init {
        tvFlag = itemView.findViewById(R.id.tv_file)
    }


}