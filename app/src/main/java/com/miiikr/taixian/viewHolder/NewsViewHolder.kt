package com.ssh.net.ssh.viewHolder

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.miiikr.taixian.R
import com.miiikr.taixian.widget.slide.SlideSwapAction
import com.ssh.net.ssh.utils.ScreenUtils

class NewsViewHolder(val context: Context, itemView: View):RecyclerView.ViewHolder(itemView), SlideSwapAction {

    override fun getActionWidth(): Int {
        return ScreenUtils.dp2px(cancel.context,62)
    }

    override fun ItemView(): View {
        return cartView
    }

    var cartView:CardView
    var cancel:TextView

    init {
        cartView = itemView.findViewById(R.id.card_view)
        cancel = itemView.findViewById(R.id.tv_cancel)
    }

}