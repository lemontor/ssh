package com.miiikr.taixian.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.ssh.net.ssh.viewHolder.NewsViewHolder

class  NewsAdapter(var context: Context,var onClickItemListener: OnClickItemListener):RecyclerView.Adapter<NewsViewHolder>(){

    var layoutInflater :LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NewsViewHolder {
        return NewsViewHolder(context,layoutInflater.inflate(R.layout.item_news,null))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: NewsViewHolder, p1: Int) {
        p0.cartView.setOnClickListener {
            onClickItemListener.clickItem(p1)
        }
    }

}