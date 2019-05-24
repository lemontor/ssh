package com.ssh.net.ssh.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssh.net.mainview.listener.ItemClickListener
import com.ssh.net.ssh.R
import com.ssh.net.ssh.`interface`.OnClickItemListener
import com.ssh.net.ssh.viewHolder.PersonViewHolder

class PersonItemAdapter(context: Context, val datas: SparseArray<String>) : RecyclerView.Adapter<PersonViewHolder>() {

    var inflater: LayoutInflater = LayoutInflater.from(context)
    lateinit var onItemClickListener: OnClickItemListener


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PersonViewHolder {
        return PersonViewHolder(inflater.inflate(R.layout.item_person, null))
    }

    override fun getItemCount(): Int {
        return datas.size()
    }

    override fun onBindViewHolder(p0: PersonViewHolder, p1: Int) {
        p0.tvItem.text = datas.get(p1)
        p0.tvItem.setOnClickListener {
            onItemClickListener.clickItem(p1)
        }
    }

    fun setItemClickListener(itemClickListener: OnClickItemListener) {
        this.onItemClickListener = itemClickListener
    }


}