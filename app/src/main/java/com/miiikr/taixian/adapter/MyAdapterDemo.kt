package com.miiikr.taixian.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.miiikr.taixian.R
import com.ssh.net.mainview.bean.ColorBean
import com.ssh.net.mainview.utils.DisPlayUtils
import com.ssh.net.mainview.view.ChanelItemView
import com.ssh.net.mainview.view.ChanelView
import com.ssh.net.ssh.viewHolder.MyViewHolder


class MyAdapterDemo(private val recyclerView: ChanelView, internal var list: List<ColorBean>, context: Context) : RecyclerView.Adapter<MyViewHolder>() {
    internal var NORMAL = 1
    internal var FOOTER = 2
    internal var disPlayWidth = 0
    internal var footerHeight = 0f
    internal var factor = 1000
    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }

    fun addOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    init {
        disPlayWidth = DisPlayUtils.getScreenWidth(context)
        footerHeight = DisPlayUtils.getScreenHeight(context) * 0.2f
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        return MyViewHolder(View.inflate(viewGroup.context, R.layout.chanel_item, null))
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
        val position = viewHolder.layoutPosition % list.size
        val myViewHolder = viewHolder as MyViewHolder
        val imageView = myViewHolder.textView
        Log.i("MyAdapter", "onBindViewHolder position = $position")
        val bitmap = BitmapFactory.decodeResource(viewHolder.itemView.context.resources, list[position].res)
        //            Glide
        //                    .with(imageView.getContext())
        //                    .load("http://smart-test1-php-1255596649.file.myqcloud.com/images/cms/566a6452a40568c09931a141a08217b1.jpg")
        //                    .into(imageView);
        imageView.setImageBitmap(bitmap)
        myViewHolder.chanelItemText.setBigText("即将于精品店上市")
        myViewHolder.itemView.tag = position
        //            myViewHolder.chanelItemText.getBigTv().setTag(viewHolder.getLayoutPosition());
        myViewHolder.chanelItemText.setSmallText("2018/19秋冬系列")
        viewHolder.itemView.setOnClickListener { v ->
            if (v is ChanelItemView) {
                if (v.f >= 1.0f) {
                    onItemClickListener!!.onItemClick(v, viewHolder.getLayoutPosition())
                } else {
                    recyclerView.onClickScrollToPosition(viewHolder.getLayoutPosition())
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return list.size * factor
    }

}
