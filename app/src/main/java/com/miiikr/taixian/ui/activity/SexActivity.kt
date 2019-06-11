package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MessageEvent
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.ssh.net.ssh.utils.AppConfig
import org.greenrobot.eventbus.EventBus

class SexActivity : BaseMvpActivity<AccountPresenter>() {

    lateinit var mIvBack: ImageView
    lateinit var mIvWomen: ImageView
    lateinit var mIvMan: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sex)
        mIvBack = findViewById(R.id.iv_back)
        mIvMan = findViewById(R.id.iv_pic_man)
        mIvWomen = findViewById(R.id.iv_pic_women)
        mIvBack.setOnClickListener {
            finish()
        }
        mIvWomen.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(AppConfig.MAIN_CHANGE_SEX_WOMEN))
            finish()
        }
        mIvMan.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(AppConfig.MAIN_CHANGE_SEX_MAN))
            finish()
        }
    }


    override fun onBackPressed() {

    }



}