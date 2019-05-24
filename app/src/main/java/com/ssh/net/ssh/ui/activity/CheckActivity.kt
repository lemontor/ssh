package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.PersonPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.CheckAdapter
import com.yo.lg.yocheck.widget.RecycleViewDivider


class CheckActivity : BaseMvpActivity<PersonPresenter>() {

    lateinit var mRvCheck: RecyclerView
    lateinit var mAdapter: CheckAdapter
    lateinit var mTvTitle:TextView
    lateinit var mTvSell:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        initUI()
        initObj()
    }

    private fun initObj() {
        val from = intent.getIntExtra("from",0)
        if(from == 0){
            mTvTitle.text = resources.getString(R.string.check_title)
            mTvSell.text = resources.getString(R.string.check_check)
        }else{
            mTvTitle.text = resources.getString(R.string.sell_title)
            mTvSell.text = resources.getString(R.string.sell_sell)
        }
        mAdapter = CheckAdapter(this)
        mRvCheck.adapter = mAdapter
    }

    private fun initUI() {
        mRvCheck = findViewById(R.id.rv_check)
        mTvTitle = findViewById(R.id.tv_title)
        mTvSell = findViewById(R.id.tv_check)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvCheck.layoutManager = linearLayoutManager
        mRvCheck.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1, resources.getColor(R.color.color_F2F2F2)))

    }

}