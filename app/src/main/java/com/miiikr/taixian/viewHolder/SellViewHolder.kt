package com.ssh.net.ssh.viewHolder

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.miiikr.taixian.R
import com.miiikr.taixian.widget.slide.SlideSwapAction
import com.ssh.net.ssh.utils.ScreenUtils

class SellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), SlideSwapAction {



    var cartView: CardView
    var cancel:TextView

    var mIvPic: ImageView
    var mTvDate: TextView
    var mTvState: TextView
    var mTvFlag: TextView
    var mTvNotify: TextView
    var mTvType:TextView


    override fun getActionWidth(): Int {
        return ScreenUtils.dp2px(cancel.context,62)
    }

    override fun ItemView(): View {
        return cartView
    }

    init {
        mIvPic = itemView.findViewById(R.id.iv_pic)
        mTvDate = itemView.findViewById(R.id.tv_date)
        mTvState = itemView.findViewById(R.id.tv_state)
        mTvFlag = itemView.findViewById(R.id.tv_flag)
        mTvNotify = itemView.findViewById(R.id.tv_notify)
//        mLayout = itemView.findViewById(R.id.layout_item)
        mTvType = itemView.findViewById(R.id.tv_type)

        cartView = itemView.findViewById(R.id.card_view)
        cancel = itemView.findViewById(R.id.tv_cancel)
    }


}