package com.miiikr.taixian.widget

import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.ssh.net.ssh.widget.BasePopupWindow
import java.util.*

class TimePickerPopupWindow(context: Context, type: Int, clickItemListener: PopupClickListener) : BasePopupWindow(context, type, clickItemListener) {

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int, clickItemListener: PopupClickListener) {
        var date = ""
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.HOUR_OF_DAY)
        val month = calender.get(Calendar.MINUTE)
        date = "$year:$month"
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        tvCancel.setOnClickListener { dismiss() }
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val datePicker = view.findViewById<TimePicker>(R.id.tp_pop)
        tvSure.setOnClickListener {
            clickItemListener.onClick(0, 0, date)
        }
        datePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            date = " $hourOfDay:$minute".trim()
        }
    }

    override fun getContentViews(): Int {
        return R.layout.popup_time
    }
}