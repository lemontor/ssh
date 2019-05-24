package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.AccountPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.SpannableUtils

class LoginActivity : BaseMvpActivity<AccountPresenter>(), View.OnClickListener {
    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.tv_login -> changeUI(true)
            v!!.id == R.id.tv_reg -> changeUI(false)
            v!!.id == R.id.tv_fast_login -> IntentUtils.toPhoneLogin(LoginActivity@ this, 1)
            v!!.id == R.id.tv_forget_pw -> IntentUtils.toPhoneLogin(LoginActivity@ this, 2)
            v!!.id == R.id.tv_login_last -> IntentUtils.toMain(LoginActivity@ this)
        }
    }


    lateinit var mTvLogin: TextView
    lateinit var mTvReg: TextView

    lateinit var mLineLogin: View
    lateinit var mLineReg: View

    lateinit var mLayoutLogin: LinearLayout
    lateinit var mLayoutReg: LinearLayout

    lateinit var mTvFastLogin: TextView
    lateinit var mTvFgPw: TextView

    lateinit var mIvQQ: ImageView
    lateinit var mIvWx: ImageView
    lateinit var mIvWb: ImageView

    lateinit var mTvSecret: TextView

    lateinit var mBtnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()
        initListener()
    }

    private fun initListener() {
        mTvLogin.setOnClickListener(this)
        mTvReg.setOnClickListener(this)
        mTvFastLogin.setOnClickListener(this)
        mTvFgPw.setOnClickListener(this)
        mBtnLogin.setOnClickListener(this)
    }

    private fun initUI() {
        mTvLogin = findViewById(R.id.tv_login)
        mTvReg = findViewById(R.id.tv_reg)

        mLineLogin = findViewById(R.id.line_login)
        mLineReg = findViewById(R.id.line_reg)

        mLayoutLogin = findViewById(R.id.layout_login)
        mLayoutReg = findViewById(R.id.layout_reg)

        mTvFastLogin = findViewById(R.id.tv_fast_login)
        mTvFgPw = findViewById(R.id.tv_forget_pw)

        mIvQQ = findViewById(R.id.iv_qq)
        mIvWx = findViewById(R.id.iv_wx)
        mIvWb = findViewById(R.id.iv_wb)

        mTvSecret = findViewById(R.id.tv_secret)

        mBtnLogin = findViewById(R.id.tv_login_last)

        var start = mTvSecret.text.toString().indexOf("《")
        var end = mTvSecret.text.toString().lastIndexOf("》") + 1
        SpannableUtils.setTextState(this, mTvSecret, mTvSecret.text.toString(), start, end,R.color.color_000000)
    }

    private fun changeUI(isLogin: Boolean) {
        if (isLogin) {
            mTvLogin.setTextColor(resources.getColor(R.color.color_000000))
            mTvReg.setTextColor(resources.getColor(R.color.color_CACACA))

            mLayoutLogin.visibility = View.VISIBLE
            mLayoutReg.visibility = View.GONE

            mLineLogin.setBackgroundResource(R.drawable.bg_line_check)
            mLineReg.setBackgroundResource(R.drawable.bg_line_uncheck)

            mTvFastLogin.visibility = View.VISIBLE
            mTvFgPw.visibility = View.VISIBLE

            mIvQQ.visibility = View.VISIBLE
            mIvWx.visibility = View.VISIBLE
            mIvWb.visibility = View.VISIBLE

            mTvSecret.visibility = View.VISIBLE
            mBtnLogin.setText(resources.getString(R.string.login_tv_login))
        } else {

            mTvLogin.setTextColor(resources.getColor(R.color.color_CACACA))
            mTvReg.setTextColor(resources.getColor(R.color.color_000000))

            mLayoutLogin.visibility = View.GONE
            mLayoutReg.visibility = View.VISIBLE

            mLineLogin.setBackgroundResource(R.drawable.bg_line_uncheck)
            mLineReg.setBackgroundResource(R.drawable.bg_line_check)

            mTvFastLogin.visibility = View.GONE
            mTvFgPw.visibility = View.GONE

            mIvQQ.visibility = View.GONE
            mIvWx.visibility = View.GONE
            mIvWb.visibility = View.GONE

            mTvSecret.visibility = View.GONE
            mBtnLogin.setText(resources.getString(R.string.login_tv_reg))
        }
    }


}