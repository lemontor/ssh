package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.PersonPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.CheckAdapter
import com.ssh.net.ssh.adapter.SubscribeAdapter
import com.yo.lg.yocheck.widget.RecycleViewDivider

class EvaluateActivity : BaseMvpActivity<PersonPresenter>() {

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
        mRvEva.adapter = CheckAdapter(this)

    }


}