package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.SinglePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.`interface`.OnClickItemListener
import com.ssh.net.ssh.adapter.NewsAdapter
import com.ssh.net.ssh.ui.fragment.NewsFragment
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.widget.slide.PlusItemSlideCallback
import com.ssh.net.ssh.widget.slide.WItemTouchHelperPlus
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