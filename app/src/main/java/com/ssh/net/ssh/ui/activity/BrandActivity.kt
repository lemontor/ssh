package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.UpdatePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.BrandAdapter
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class BrandActivity:BaseMvpActivity<UpdatePresenter>() {

    lateinit var mRvBrand:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)
        initUI()
    }

    private fun initUI() {
        mRvBrand = findViewById(R.id.rv_brand)
        var  linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvBrand.layoutManager = linearLayoutManager
        mRvBrand.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL))
        mRvBrand.adapter = BrandAdapter(this)
    }

}