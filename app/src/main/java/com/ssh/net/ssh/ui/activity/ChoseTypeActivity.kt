package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.SinglePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.TypeAdapter

class ChoseTypeActivity : BaseMvpActivity<SinglePresenter>() {

    lateinit var mTvTitle: TextView
    lateinit var mRvType: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        initUI()
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mRvType = findViewById(R.id.rv_sub)
        mRvType.setBackgroundResource(android.R.color.white)
        mTvTitle.text = resources.getString(R.string.chose_type_title)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvType.layoutManager = layoutManager
        mRvType.adapter = TypeAdapter(this)
    }

}