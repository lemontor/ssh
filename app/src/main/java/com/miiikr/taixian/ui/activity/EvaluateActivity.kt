package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.CheckAdapter
import com.yo.lg.yocheck.widget.RecycleViewDivider

class EvaluateActivity : BaseMvpActivity<PersonPresenter>(),OnClickItemListener {
    override fun clickItem(position: Int) {
    }

    lateinit var mRvEva: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        initUI()
    }

    private fun initUI() {
        mRvEva = findViewById(R.id.rv_eva)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvEva.layoutManager = linearLayoutManager
        mRvEva.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1, R.color.color_F2F2F2))
        mRvEva.adapter = CheckAdapter(this,this)

    }


}