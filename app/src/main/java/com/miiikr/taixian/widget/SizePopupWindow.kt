package com.ssh.net.ssh.widget

import android.content.Context
import android.view.View
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener

class SizePopupWindow(context: Context,onClickItemListener: PopupClickListener):BasePopupWindow(context,0,onClickItemListener) {

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int,clickItemListener: PopupClickListener) {
    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_size

    }
}