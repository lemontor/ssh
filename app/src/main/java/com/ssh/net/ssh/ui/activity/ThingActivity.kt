package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.UpdatePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.IntentUtils

class ThingActivity : BaseMvpActivity<UpdatePresenter>(),View.OnClickListener {
    override fun onClick(v: View?) {
        when{
            v!!.id ==  R.id.layout_brand -> IntentUtils.toBrand(this)
        }
    }

    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goos_details)
        initUI()
        initListener()
    }

    private fun initListener() {
        mLayoutBrand.setOnClickListener(this)
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mLayoutBrand = findViewById(R.id.layout_brand)

    }


}