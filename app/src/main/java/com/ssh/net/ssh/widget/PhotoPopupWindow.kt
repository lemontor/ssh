package com.ssh.net.ssh.widget

import android.content.Context
import android.view.View
import android.widget.TextView
import com.ssh.net.ssh.R

class PhotoPopupWindow(val context: Context):BasePopupWindow(context,0) {

    lateinit var tvTakePhoto:TextView
    lateinit var tvChosePhoto:TextView
    lateinit var tvCancel:TextView

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int) {
        tvTakePhoto = view.findViewById(R.id.tv_take)
        tvChosePhoto = view.findViewById(R.id.tv_chose)
        tvCancel = view.findViewById(R.id.tv_cancel)
        tvCancel.setOnClickListener {
            if(isShowing){
                dismiss()
            }
        }
    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_photo
    }
}