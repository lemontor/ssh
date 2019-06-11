package com.ssh.net.ssh.viewHolder

import android.support.design.internal.BottomNavigationItemView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.miiikr.taixian.R

class PersonViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var tvItem:TextView

    init {
         tvItem = itemView.findViewById(R.id.tv)
    }

}