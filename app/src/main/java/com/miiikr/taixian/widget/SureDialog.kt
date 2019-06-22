package com.miiikr.taixian.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.miiikr.taixian.R

class SureDialog(context: Context, val layoutResID: Int) : Dialog(context, R.style.DialogTheme1) {

    var con: Context? = null

    init {
        con = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID)
        val dialogWindow = window
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        lp.gravity = Gravity.CENTER
        window.attributes = lp
        setCanceledOnTouchOutside(true)
    }


}