package com.ssh.net.ssh.BaseMvp.View

import com.ssh.net.ssh.BaseMvp.IView.BaseView
import com.ssh.net.ssh.BaseMvp.presenter.BasePresenter

open class BaseMvpFragment<T: BasePresenter<*>>:BaseFragment(),BaseView {
    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun error() {
    }

    lateinit var mPresenter:T

}