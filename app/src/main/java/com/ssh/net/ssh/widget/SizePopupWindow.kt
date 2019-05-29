package com.ssh.net.ssh.widget

import android.content.Context
import android.view.View
import com.ssh.net.ssh.R

class SizePopupWindow(context: Context):BasePopupWindow(context,0) {

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int) {
    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_size

    }
}