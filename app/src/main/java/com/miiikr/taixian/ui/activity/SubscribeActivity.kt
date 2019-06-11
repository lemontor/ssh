package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.SubscribeAdapter

class SubscribeActivity : BaseMvpActivity<PersonPresenter>() {

    lateinit var mRvSub: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        initUI()
    }

    private fun initUI() {
        mRvSub = findViewById(R.id.rv_sub)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvSub.layoutManager = linearLayoutManager
        mRvSub.adapter = SubscribeAdapter(this)
    }


}