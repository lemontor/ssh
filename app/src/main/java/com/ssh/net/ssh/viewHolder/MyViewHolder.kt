package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.ssh.net.mainview.view.MyItemTextLayout
import com.ssh.net.ssh.R

class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    var textView: ImageView
    var chanelItemText: MyItemTextLayout

    init {
        textView = itemView.findViewById(R.id.iv)
        chanelItemText = itemView.findViewById(R.id.chanelItemText)
    }
}