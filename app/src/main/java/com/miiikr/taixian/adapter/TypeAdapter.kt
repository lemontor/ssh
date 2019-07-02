package com.miiikr.taixian.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.ProductEntity
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.viewHolder.TypeViewHolder

class TypeAdapter(val context: Context, val datas: ArrayList<ProductEntity.DetailsEntity>, val type: Int) : RecyclerView.Adapter<TypeViewHolder>() {

    val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TypeViewHolder {
        return TypeViewHolder(layoutInflater.inflate(R.layout.item_type, p0, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: TypeViewHolder, p1: Int) {
        if (datas[p1].img != null) {
            GlideHelper.loadBitmapByNormalWithPlaceholder(context as Activity, p0.mIvPic, datas[p1].img!!, R.mipmap.icon_empty_type)
        }
        p0.mIvPic.setOnClickListener {
            when (p1) {
                0 -> IntentUtils.toGoodsDetails(context, 1, type)
                1 -> IntentUtils.toGoodsDetails(context, 2, type)
                2 -> IntentUtils.toGoodsDetails(context, 3, type)
            }

        }
    }
}