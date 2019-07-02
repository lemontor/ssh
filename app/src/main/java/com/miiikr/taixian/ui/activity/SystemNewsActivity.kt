package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.NewsAdapter
import com.miiikr.taixian.ui.fragment.NewsFragment
import com.miiikr.taixian.widget.slide.PlusItemSlideCallback
import com.miiikr.taixian.widget.slide.WItemTouchHelperPlus
import com.ssh.net.ssh.utils.ScreenUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class SystemNewsActivity : BaseMvpActivity<SinglePresenter>() {

    lateinit var mTvTitle: TextView
    lateinit var mRvNews: RecyclerView
    var mFragmentManager: FragmentManager

    init {
        mFragmentManager = supportFragmentManager
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initUI()
    }

    private fun initUI() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        mTvTitle = findViewById(R.id.tv_title)
        mRvNews = findViewById(R.id.rv_sub)
        mTvTitle.text = resources.getString(R.string.system_news_title)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvNews.layoutManager = linearLayoutManager
        mRvNews.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, ScreenUtils.dp2px(this, 10), resources.getColor(R.color.color_F2F2F2)))
        mRvNews.adapter = NewsAdapter(this, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                val fragment = NewsFragment()
                showContentFragment(fragment)
            }
        })
        val callback = PlusItemSlideCallback()
        val extension = WItemTouchHelperPlus(callback)
        extension.attachToRecyclerView(mRvNews)
    }

    fun showContentFragment(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_content, fragment)
        transaction.commit()
    }



}