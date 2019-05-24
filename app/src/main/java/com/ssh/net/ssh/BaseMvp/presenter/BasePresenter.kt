package com.ssh.net.ssh.BaseMvp.presenter

import com.ssh.net.ssh.BaseMvp.IView.BaseView

open class BasePresenter<T: BaseView> {
    lateinit var mView:T
}