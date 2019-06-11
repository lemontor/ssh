package com.miiikr.taixian.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.CheckAdapter
import com.ssh.net.ssh.utils.IntentUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider


class CheckActivity : BaseMvpActivity<PersonPresenter>(),OnClickItemListener {

    override fun clickItem(position: Int) {
        IntentUtils.toDetails(this)
    }

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
        mAdapter = CheckAdapter(this,this)
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