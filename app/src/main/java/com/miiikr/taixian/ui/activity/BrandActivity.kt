package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.UpdatePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.BrandAdapter
import com.miiikr.taixian.utils.RequestInterface
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class BrandActivity: BaseMvpActivity<UpdatePresenter>(),MainView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mRvBrand:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)
        mPresenter = UpdatePresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
    }

    private fun initData() {
        mPresenter.getBrand(RequestInterface.REQUEST_WATCH_BEAND_ID,"1")
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}