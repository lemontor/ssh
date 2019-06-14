package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.SpannableUtils

class LoginActivity : BaseMvpActivity<AccountPresenter>(), AccountView {
    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if(RequestInterface.REQUEST_CODE_ID == responseId){
            val result = response as? CommonEntity
            if (result != null && result.state == 1) {
                mTvCode.isEnabled = false
                mPresenter.calculateTime(mTvCode)
                Toast.makeText(LoginActivity@ this, "获取验证码成功", Toast.LENGTH_SHORT).show()
            }
        }else if(RequestInterface.REQUEST_LOGIN_ID == responseId){
            val result = response as? LoginEntity
            if (result != null) {
                if(result.state == 1){
                    mPresenter.putValues(result)
                    if(!SharedPreferenceUtils(this@LoginActivity).isChose(SharedPreferenceUtils.PREFERENCE_U_C)){
                        IntentUtils.toSex(this@LoginActivity)
                    }
                    finish()
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
            Toast.makeText(LoginActivity@ this, msg, Toast.LENGTH_SHORT).show()

    }

    lateinit var mTvBack:TextView
    lateinit var mTvLogin: TextView
    lateinit var mTvCode: TextView
    lateinit var mEdtPhone: EditText
    lateinit var mEdtCode: EditText
    lateinit var mIvWx: ImageView
    lateinit var mSSHProgressHUD: SSHProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
        initUI()
        initListener()
    }

    private fun initListener() {
        mTvCode.setOnClickListener {
            mPresenter.getCode(RequestInterface.REQUEST_CODE_ID, mEdtPhone.text.toString())
        }

        mTvLogin.setOnClickListener {
            mPresenter.doLogin(RequestInterface.REQUEST_LOGIN_ID, mEdtPhone.text.toString(), mEdtCode.text.toString())
        }

        mTvBack.setOnClickListener {
            finish()
        }
    }


    private fun initUI() {
        mTvBack = findViewById(R.id.tv_back)
        mTvCode = findViewById(R.id.tv_code)
        mTvLogin = findViewById(R.id.tv_next)

        mEdtPhone = findViewById(R.id.edt_account)
        mEdtCode = findViewById(R.id.edt_code)
        mIvWx = findViewById(R.id.iv_wx)
        mSSHProgressHUD = SSHProgressHUD.getInstance(this)
        mSSHProgressHUD.setMessage("正在处理中")
        mSSHProgressHUD.setCancelable(true)
    }

    override fun showLoading() {
        super.showLoading()
        mSSHProgressHUD.show()
    }

    override fun hideLoading() {
        super.hideLoading()
        mSSHProgressHUD.dismiss()
    }


    override fun onDestroy() {
        mPresenter.closeTime()
        mPresenter.detachView()
        super.onDestroy()
    }



}