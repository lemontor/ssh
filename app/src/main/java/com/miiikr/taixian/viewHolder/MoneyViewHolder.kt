package com.miiikr.taixian.viewHolder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.miiikr.taixian.R
import com.miiikr.taixian.widget.slide.SlideSwapAction
import com.ssh.net.ssh.utils.ScreenUtils

class MoneyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), SlideSwapAction {


    override fun getActionWidth(): Int {
        return ScreenUtils.dp2px(tvCancel.context,51)
    }

    override fun ItemView(): View {
        return cartView
    }

    var tvTitle:TextView
    var tvDate:TextView
    var tvMoney:TextView
    var cartView: CardView
    var tvCancel:TextView

    init {
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvDate = itemView.findViewById(R.id.tv_date)
        tvMoney = itemView.findViewById(R.id.tv_money)
        tvCancel = itemView.findViewById(R.id.tv_cancel)
        cartView = itemView.findViewById(R.id.card_view)
    }








}