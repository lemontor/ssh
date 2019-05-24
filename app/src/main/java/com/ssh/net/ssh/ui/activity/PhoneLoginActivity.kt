package com.ssh.net.ssh.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.AccountPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.SpannableUtils

class PhoneLoginActivity : BaseMvpActivity<AccountPresenter>() {

    lateinit var mTvTitle: TextView
    lateinit var mBtnNext: Button
    lateinit var mTvNotify: TextView
    lateinit var mEdtNum: EditText
    lateinit var mTvNewPw: TextView
    lateinit var mTvBack:TextView
    var from = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_login)
        initUI()
        initData()
        initListener()
    }


    private fun initListener() {
        mBtnNext.setOnClickListener {
            IntentUtils.toCheckCode(PhoneLoginActivity@ this, "13071190733")
        }
    }


    private fun initData() {
        from = intent.getIntExtra("from", 0)
        when (from) {
            1 -> {
                mTvTitle.setText(R.string.phone_login_notify_title)
                mBtnNext.setText(R.string.phone_login_notify_next)
                mTvNotify.visibility = View.GONE
                mTvNewPw.visibility = View.GONE
            }
            2 -> {
                mTvTitle.setText(R.string.phone_login_notify_find)
                mBtnNext.setText(R.string.phone_login_notify_code)
                mTvNotify.visibility = View.VISIBLE
                mTvNewPw.visibility = View.GONE
                setTextState()
            }
            3 -> {
                mTvTitle.setText(R.string.phone_login_notify_update)
                mBtnNext.setText(R.string.phone_login_notify_send)
                mTvNotify.visibility = View.GONE
                mTvNewPw.visibility = View.VISIBLE
                mEdtNum.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
            }
        }


    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mBtnNext = findViewById(R.id.tv_next)
        mTvNotify = findViewById(R.id.tv_notify)
        mEdtNum = findViewById(R.id.edt_phone_num)
        mTvNewPw = findViewById(R.id.tv_new_pw)
        mTvBack = findViewById(R.id.tv_back)
        var drawable = resources.getDrawable(R.mipmap.icon_back)
        mTvBack.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null)
    }

    private fun setTextState() {
        SpannableUtils.setTextState(this, mTvNotify, mTvNotify.text.toString(), 6, 10,R.color.color_000000)
    }


}