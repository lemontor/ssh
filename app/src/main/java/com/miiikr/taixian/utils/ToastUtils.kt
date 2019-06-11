package com.miiikr.taixian.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {

    fun toastShow(context: Context,msg:String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

}