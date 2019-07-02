package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CashDetailsEntity
import com.miiikr.taixian.viewHolder.MoneyViewHolder
import com.ssh.net.ssh.utils.SpannableUtils

class MoneyDetailsAdapter(var context: Context,val data:ArrayList<CashDetailsEntity.CashListEntity>):RecyclerView.Adapter<MoneyViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoneyViewHolder {
        return MoneyViewHolder(View.inflate(context,R.layout.item_money,null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: MoneyViewHolder, p1: Int) {
        p0.tvCancel.setOnClickListener {  }
        p0.tvTitle.text = data[p1].ordersExplain
        p0.tvDate.text = SpannableUtils.millisecond2Date(data[p1].createTime)
        p0.tvMoney.text = "${data[p1].ordersMoney}"
    }

}