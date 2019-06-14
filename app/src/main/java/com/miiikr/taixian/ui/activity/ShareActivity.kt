package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.IntentUtils

class ShareActivity : BaseMvpActivity<SinglePresenter>() {
    lateinit var mTvCount:TextView
    lateinit var mTvNow:TextView
    lateinit var mTvAll:TextView
    lateinit var mIvBack:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        initUI()
        findViewById<Button>(R.id.btn_share).setOnClickListener {
              IntentUtils.toPost(this@ShareActivity)
        }
        mIvBack = findViewById(R.id.iv_back)
        mIvBack .setOnClickListener {
            finish()
        }
    }

    private fun initUI() {
        mTvCount = findViewById(R.id.tv_num)
        mTvNow = findViewById(R.id.tv_money_get)
        mTvAll = findViewById(R.id.tv_money_all)
    }

}