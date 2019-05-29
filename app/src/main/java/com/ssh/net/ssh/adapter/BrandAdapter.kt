package com.ssh.net.ssh.adapter

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.utils.SpannableUtils
import com.ssh.net.ssh.viewHolder.BrandViewHolder

class BrandAdapter(var context: Context) : RecyclerView.Adapter<BrandViewHolder>() {

    var clickFlag = -1

    var layoutInflater: LayoutInflater
    var brandViewHolder: BrandViewHolder? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BrandViewHolder {
        return BrandViewHolder(layoutInflater.inflate(R.layout.item_brand, p0, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: BrandViewHolder, p1: Int) {
        p0.layout.setOnClickListener {
            if (clickFlag != -1) {
                if (clickFlag == p1) {//选择

                } else {//展开
                    if (brandViewHolder != null) {
                        heightReduce(brandViewHolder!!.layout)
                    }
                    heightExpand(p0.layout)
                    brandViewHolder = p0
                    clickFlag = p1
                }
            } else {//展开
                heightExpand(p0.layout)
                brandViewHolder = p0
                clickFlag = p1
            }
        }
        p0.mTvIndex.text = p1.toString()
    }

    fun heightExpand(view: View) {
        colorForBlack(view)
        val valueAnimator = ValueAnimator.ofInt(view.layoutParams.height, ScreenUtils.dp2px(context, 122))
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            view.layoutParams.height = value
            view.requestLayout()
        }
        valueAnimator.start()
    }

    fun heightReduce(view: View) {
        colorForWhite(view)
        val valueAnimator = ValueAnimator.ofInt(view.layoutParams.height, ScreenUtils.dp2px(context, 60))
        valueAnimator.duration = 500
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            view.layoutParams.height = value
            view.requestLayout()
        }
        valueAnimator.start()
    }

    fun colorForBlack(view: View){
        val animator = ValueAnimator.ofInt(context.resources.getColor(R.color.color_000000), context.resources.getColor(R.color.color_00000f),context.resources.getColor(R.color.color_ffffff))
        animator.setEvaluator(ArgbEvaluator())
        animator.duration = 500
        animator.start()
        animator.addUpdateListener { animation -> view.setBackgroundColor(animation.animatedValue as Int) }
    }

    fun colorForWhite(view: View){
        val animator = ValueAnimator.ofInt(context.resources.getColor(R.color.color_ffffff), context.resources.getColor(R.color.color_fffff0),context.resources.getColor(R.color.color_000000))
        animator.setEvaluator(ArgbEvaluator())
        animator.duration = 500
        animator.start()
        animator.addUpdateListener { animation -> view.setBackgroundColor(animation.animatedValue as Int) }
    }


}