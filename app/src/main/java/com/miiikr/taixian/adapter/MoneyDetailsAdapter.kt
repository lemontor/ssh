package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.miiikr.taixian.viewHolder.MoneyViewHolder

class MoneyDetailsAdapter(var context: Context):RecyclerView.Adapter<MoneyViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoneyViewHolder {
        return MoneyViewHolder(View.inflate(context,R.layout.item_money,null))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: MoneyViewHolder, p1: Int) {

    }

}