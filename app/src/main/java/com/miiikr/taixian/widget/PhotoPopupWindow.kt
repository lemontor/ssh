package com.ssh.net.ssh.widget

import android.content.Context
import android.view.View
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener

class PhotoPopupWindow(val context: Context,onClickItemListener: PopupClickListener):BasePopupWindow(context,0,onClickItemListener) {

    lateinit var tvTakePhoto:TextView
    lateinit var tvChosePhoto:TextView
    lateinit var tvCancel:TextView

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int,clickItemListener: PopupClickListener) {
        tvTakePhoto = view.findViewById(R.id.tv_take)
        tvChosePhoto = view.findViewById(R.id.tv_chose)
        tvCancel = view.findViewById(R.id.tv_cancel)
        tvCancel.setOnClickListener {
            if(isShowing){
                dismiss()
            }
        }
        tvTakePhoto.setOnClickListener {
            clickItemListener.onClick(1,0,"")
        }

        tvChosePhoto.setOnClickListener {
            clickItemListener.onClick(2,0,"")
        }

    }

    override fun getContentViews(): Int {
        return R.layout.layout_popupwindow_photo
    }
}