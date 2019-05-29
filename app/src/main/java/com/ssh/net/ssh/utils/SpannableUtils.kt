package com.ssh.net.ssh.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.LeadingMarginSpan
import android.widget.TextView

class SpannableUtils {

    companion object {
        fun setTextState(context: Context, textView: TextView, content: String, start: Int, end: Int, color: Int) {
            var spannableString = SpannableString(content)
            val foregroundColorSpan = ForegroundColorSpan(context.resources.getColor(color))
            spannableString.setSpan(foregroundColorSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            textView.text = spannableString
        }


        fun setMargin(context: Context,textView: TextView) {
            var spannableString = SpannableString(textView.text.toString())
            var what = LeadingMarginSpan.Standard(0,ScreenUtils.dp2px(context,15))
            spannableString.setSpan(what, 0, spannableString.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
            textView.text = spannableString
        }




    }

}