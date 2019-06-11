package com.miiikr.taixian.BaseMvp.presenter

import com.miiikr.taixian.BaseMvp.IView.BaseView
import com.miiikr.taixian.BaseMvp.IView.MainView


open class BasePresenter<T: BaseView> {
    lateinit var mView:T
}