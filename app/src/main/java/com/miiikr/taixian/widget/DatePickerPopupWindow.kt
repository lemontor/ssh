package com.miiikr.taixian.widget

import android.content.Context
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.ssh.net.ssh.widget.BasePopupWindow
import java.util.*

class DatePickerPopupWindow(context: Context, type: Int, clickItemListener: PopupClickListener) : BasePopupWindow(context, type, clickItemListener) {

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int, clickItemListener: PopupClickListener) {
        var date = ""
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        date = "$year-${month + 1}-$day"
        val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
        val tvSure = view.findViewById<TextView>(R.id.tv_sure)
        val datePicker = view.findViewById<DatePicker>(R.id.dp_pop)
        tvCancel.setOnClickListener { dismiss() }
        tvSure.setOnClickListener {
            clickItemListener.onClick(0, 0, date)
        }
        datePicker.init(year, month, day) { view, year, monthOfYear, dayOfMonth ->
            date = "$year-${monthOfYear + 1}-$dayOfMonth".trim()
        }
    }

    override fun getContentViews(): Int {
        return R.layout.popup_date
    }
}