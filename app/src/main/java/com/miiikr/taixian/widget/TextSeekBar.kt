package com.ssh.net.ssh.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import com.miiikr.taixian.R
import android.view.ViewTreeObserver.OnGlobalLayoutListener


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
        mSbar.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mSbar.viewTreeObserver.removeGlobalOnLayoutListener(this)
                mTvFlag.text = mSbar.progress.toString()
                //获取文本宽度
                val textWidth = mTvFlag.width.toFloat()
                //获取seekbar最左端的x位置
                val left = mSbar.left.toFloat()
                //进度条的刻度值
                val max = Math.abs(mSbar.max).toFloat()
                //这不叫thumb的宽度,叫seekbar距左边宽度,实验了一下，seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,我这里设置15dp;
                val thumb = dip2px(context, 15f).toFloat()
                //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度
                val average = (mSbar.width.toFloat() - 2 * thumb) / max
                //int to float
                val currentProgress = mSbar.progress.toFloat()
                //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
                val pox = left - textWidth / 2 + thumb + average * currentProgress
                mTvFlag.x = pox
            }
        })
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
                when {
                    progress <= 65 -> onSeekListener!!.onSeek("其他  无法判断成色","七成新以下")
                    progress <= 75 -> onSeekListener!!.onSeek("7新 有明显划痕、污渍磨损","7成新")
                    progress <= 85 -> onSeekListener!!.onSeek("85成新 日常使用痕迹 有少量划痕、污渍磨损","85成新")
                    progress <= 90 -> onSeekListener!!.onSeek("9成新 日常使用痕迹 商品状态良好","9成新")
                    progress <= 95 -> onSeekListener!!.onSeek("95新 轻微使用，商品状态几乎接近新品","95新 ")
                    progress <= 99 -> onSeekListener!!.onSeek("99新 基本上未使用过","99新")
                    progress <= 100 -> onSeekListener!!.onSeek("全新 从未使用过","全新")
                }
            }
        })
        mSbar.progress = 100
    }

    fun getTextCount(): String {
        return mTvFlag.text.toString()
    }


    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun setOnSeekListener(listener:OnSeekListener){
        onSeekListener = listener
    }

    private var onSeekListener:OnSeekListener?=null

    interface OnSeekListener {
       fun onSeek(value:String,old:String)
    }

}