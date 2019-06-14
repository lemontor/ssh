package com.miiikr.taixian.widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.R

class LoadingUtils {


    companion object{

        fun  showLoadDialog(context: Context,msg:String,isCancelable:Boolean){
            val inflater = LayoutInflater.from(context)
            val contentView = inflater.inflate(R.layout.dialog_progress,null)
            var tvMsg = contentView.findViewById<TextView>(R.id.textview_message)
            var ivImg = contentView.findViewById<ImageView>(R.id.imageview_progress_spinner)

        }

    }





}