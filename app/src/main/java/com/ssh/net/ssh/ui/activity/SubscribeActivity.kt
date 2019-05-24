package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.PersonPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.adapter.SubscribeAdapter

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