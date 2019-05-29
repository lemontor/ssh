package com.ssh.net.ssh.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ssh.net.ssh.utils.ScreenUtils

class MarginItemDecoration(val context: Context,val space:Int):RecyclerView.ItemDecoration() {
    var margin:Int
    init {
        margin = ScreenUtils.dp2px(context,space)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.left = margin
        outRect.bottom = margin

//        if (parent.getChildAdapterPosition(view) % 3 == 0){
//            outRect.left = 0
//        }
    }
}