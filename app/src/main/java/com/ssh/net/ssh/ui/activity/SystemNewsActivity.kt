package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.SinglePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.NewsAdapter
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class SystemNewsActivity:BaseMvpActivity<SinglePresenter>(){

    lateinit var mTvTitle:TextView
    lateinit var mRvNews:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initUI()
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mRvNews = findViewById(R.id.rv_sub)
        mTvTitle.text = resources.getString(R.string.system_news_title)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvNews.layoutManager = linearLayoutManager
        mRvNews.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, SpannableUtils.dp2px(this,10), resources.getColor(R.color.color_F2F2F2)))
        mRvNews.adapter = NewsAdapter(this)
    }


}