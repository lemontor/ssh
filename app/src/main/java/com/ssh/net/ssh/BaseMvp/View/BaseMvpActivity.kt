package com.ssh.net.ssh.BaseMvp.View

import com.ssh.net.ssh.BaseMvp.IView.BaseView
import com.ssh.net.ssh.BaseMvp.presenter.BasePresenter

open class BaseMvpActivity<T: BasePresenter<*>>:BaseActivity(), BaseView {

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun error() {

    }

    lateinit var mPresenter:T

}