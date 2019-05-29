package com.ssh.net.ssh.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import com.ssh.net.ssh.R

class TextSeekBar : RelativeLayout {

    lateinit var mTvFlag: TextView
    lateinit var mSbar: SeekBar
    var scroll: Boolean = false

    constructor(context: Context) : super(context) {
        initUI(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUI(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI(context)
    }

    fun initUI(context: Context) {
        val contentView = View.inflate(context, R.layout.layout_text_seekbar, this)
        mTvFlag = contentView.findViewById(R.id.seek_text)
        mSbar = contentView.findViewById(R.id.seek_bar)
        val leftIndex = mSbar.left.toFloat()
        Log.e("tag_text_x", "" + mTvFlag.x + "    " + leftIndex)
        mSbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (!scroll) {
                    mTvFlag.setPadding(0, 0, 0, 0)
                    scroll = true
                }
                //进度是从-50~50的,但是seekbar.getmin()有限制,所以这里用0~100 -50;
                //                int text = progress - 50;
                //设置文本显示
                mTvFlag.text = progress.toString()
                //获取文本宽度
                val textWidth = mTvFlag.width.toFloat()
                //获取seekbar最左端的x位置
                val left = seekBar.left.toFloat()
                //进度条的刻度值
                val max = Math.abs(seekBar.max).toFloat()
                //这不叫thumb的宽度,叫seekbar距左边宽度,实验了一下，seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,我这里设置15dp;
                val thumb = dip2px(context, 15f).toFloat()
                //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度
                val average = (seekBar.width.toFloat() - 2 * thumb) / max
                //int to float
                val currentProgress = progress.toFloat()
                //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
                val pox = left - textWidth / 2 + thumb + average * currentProgress
                mTvFlag.x = pox
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val progress = seekBar.progress
                when (progress) {
                    99 -> ""
                    98 or 97 -> ""
                    96 or 95 -> ""
                }
            }
        })
    }

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


}