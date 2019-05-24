package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ssh.net.ssh.R

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var cartView:CardView

    init {
        cartView = itemView.findViewById(R.id.card_view)
    }

}