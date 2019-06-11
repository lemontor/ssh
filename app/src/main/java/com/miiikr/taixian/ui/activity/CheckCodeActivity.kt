package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.text.SpannableString
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.RequestManager
import com.ssh.net.ssh.widget.EditCodeView

class CheckCodeActivity : BaseMvpActivity<AccountPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestManager.ACCOUNT_SET_STATE) {
            var result = response as? SpannableString
            if (result != null)
                mTvNotify.text = result
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var editCodeView: EditCodeView
    lateinit var mTvNotify: TextView
    lateinit var mTvTime: TextView
    lateinit var mTvBack:TextView

    var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_code)
        mPresenter = AccountPresenter()
        mPresenter.mView = this
        initUI()
        initObj()
        initListener()
    }

    private fun initObj() {
        phone = intent.getStringExtra("phone")

    }

    private fun initListener() {
        editCodeView.setOnCodeComplete(object : EditCodeView.OnCodeComplete {
            override fun onComplete(code: String) {

            }
        })
        mPresenter.resetPhone(this,RequestManager.ACCOUNT_SET_STATE,phone!!)
        mPresenter.calculateTime(mTvTime)
    }

    private fun initUI() {
        editCodeView = findViewById(R.id.edit_code)
        mTvNotify = findViewById(R.id.tv_code_info)
        mTvTime = findViewById(R.id.tv_notify_time)
        mTvBack = findViewById(R.id.tv_back)
        var drawable = resources.getDrawable(R.mipmap.icon_back)
        mTvBack.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null)
    }

    override fun onDestroy() {
        mPresenter.closeTime()
        super.onDestroy()
    }


}