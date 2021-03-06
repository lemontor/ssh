package com.miiikr.taixian.BaseMvp.View

import com.miiikr.taixian.BaseMvp.IView.BaseView
import com.miiikr.taixian.BaseMvp.presenter.BasePresenter


open class BaseMvpActivity<T: BasePresenter<*>>:BaseActivity(), BaseView {

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun error() {

    }

    lateinit var mPresenter:T

}