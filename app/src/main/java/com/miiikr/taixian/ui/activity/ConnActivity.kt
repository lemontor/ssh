package com.miiikr.taixian.ui.activity

import android.os.Bundle
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R

class ConnActivity:BaseMvpActivity<MainPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conn)
    }

}