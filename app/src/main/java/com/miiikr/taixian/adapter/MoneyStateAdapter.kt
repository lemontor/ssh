package com.miiikr.taixian.adapter

import android.content.ClipData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MoneyStateEntity
import com.miiikr.taixian.viewHolder.MoneyStateViewHolder

class MoneyStateAdapter(context: Context, val data: ArrayList<MoneyStateEntity.MoneyStateDetailsEntity>, val from: Int, val onClickItemListener: OnClickItemListener) : RecyclerView.Adapter<MoneyStateViewHolder>() {

    var layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoneyStateViewHolder {
        return MoneyStateViewHolder(layoutInflater.inflate(R.layout.item_money_state, null, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MoneyStateViewHolder, p1: Int) {
        when (from) {
            1 -> {
                if (data[p1].state == 1) {
                    p0.ivPic.setImageResource(R.mipmap.icon_no_money)
                    p0.ivPic.setOnClickListener {
                        onClickItemListener.clickItem(p1)
                    }
                } else {
                    p0.ivPic.setImageResource(R.mipmap.icon_get_money)
                }
            }
            2 -> {
                if (data[p1].bonusesType == 1) {//未成单
                    p0.ivPic.setImageResource(R.mipmap.icon_no_complete)
                } else if (data[p1].bonusesType == 2) {//等待领取
                    p0.ivPic.setImageResource(R.mipmap.icon_no_money)
                } else if (data[p1].bonusesType == 3) {//已领取
                    p0.ivPic.setImageResource(R.mipmap.icon_get_money)
                }
            }
            3 -> p0.ivPic.setImageResource(R.mipmap.icon_no_complete)

        }


    }
}