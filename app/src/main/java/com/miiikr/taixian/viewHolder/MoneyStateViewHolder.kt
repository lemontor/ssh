package com.miiikr.taixian.viewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.miiikr.taixian.R

class MoneyStateViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var ivPic:ImageView

    init {
        ivPic = itemView.findViewById(R.id.iv_money)
    }


}