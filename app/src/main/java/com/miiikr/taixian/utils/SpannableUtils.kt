package com.ssh.net.ssh.utils

import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.LeadingMarginSpan
import android.widget.TextView
import android.text.style.RelativeSizeSpan
import java.text.SimpleDateFormat
import java.util.*


class SpannableUtils {

    companion object {
        fun setTextState(context: Context, textView: TextView, content: String, start: Int, end: Int, color: Int) {
            var spannableString = SpannableString(content)
            val foregroundColorSpan = ForegroundColorSpan(context.resources.getColor(color))
            spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
        }

        fun setTextStateColorAndSize(context: Context, textView: TextView, start: Int, end: Int, color: Int) {
            var spannableString = SpannableStringBuilder(textView.text.toString().trim())
            val foregroundColorSpan = ForegroundColorSpan(color)
            val sizeSpan = RelativeSizeSpan(2.0f)
            spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
        }



        fun setMargin(context: Context, textView: TextView) {
            var spannableString = SpannableString(textView.text.toString())
            var what = LeadingMarginSpan.Standard(0, ScreenUtils.dp2px(context, 15))
            spannableString.setSpan(what, 0, spannableString.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = spannableString
        }

        fun setTextSize(textView: TextView) {
            var spannableString = SpannableString(textView.text.toString())
            var   endIndex= textView.text.toString().indexOf("已")
            val sizeSpan01 = RelativeSizeSpan(1.8f)
            val sizeSpan02 = RelativeSizeSpan(0.8f)
            spannableString.setSpan(sizeSpan01, 1, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(sizeSpan02, endIndex, textView.text.toString().length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
        }

        fun setTextSize2(textView: TextView) {
            var spannableString = SpannableString(textView.text.toString())
            var   endIndex= textView.text.toString().indexOf("未")
            val sizeSpan01 = RelativeSizeSpan(1.8f)
            val sizeSpan02 = RelativeSizeSpan(0.8f)
            spannableString.setSpan(sizeSpan01, 1, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spannableString.setSpan(sizeSpan02, endIndex, textView.text.toString().length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
        }


        fun millisecond2Date(m:Long):String{
            var date = Date()
            date.time = m
            return SimpleDateFormat("yyyy/MM/dd hh:mm").format(date).toString()
        }




    }

}