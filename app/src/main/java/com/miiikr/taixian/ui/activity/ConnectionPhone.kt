package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.ssh.net.ssh.utils.IntentUtils

class ConnectionPhone:BaseMvpActivity<AccountPresenter>(),AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if(responseId == RequestInterface.REQUEST_CODE_ID){
            val result = response as? CommonEntity
            if (result != null && result.state == 1) {
                mTvCode.isEnabled = false
                mPresenter.calculateTime(mTvCode)
                Toast.makeText(LoginActivity@ this, "获取验证码成功", Toast.LENGTH_SHORT).show()
            }
        }else if(responseId == RequestInterface.REQUEST_HAS_PHONE_ID){
            val result = response as? LoginEntity
            if (result != null) {
                if (result.state == 1) {
                    mPresenter.putValues(result)
//                    if (!SharedPreferenceUtils(this@LoginActivity).isChose(SharedPreferenceUtils.PREFERENCE_U_C)) {
//                        IntentUtils.toSex(this@LoginActivity)
//                    }
                    finish()
                }
            }
        }


    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mEdtPhone:EditText
    lateinit var mEdtCode:EditText
    lateinit var mTvCode:TextView
    lateinit var mTvSure:TextView

    var targetId = ""
    var mode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conn_phone)
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
        initUI()
        initListener()
        targetId = intent.getStringExtra("targetId")
        mode = intent.getStringExtra("mode")

    }

    private fun initUI() {
        mEdtPhone = findViewById(R.id.edt_name)
        mEdtCode = findViewById(R.id.edt_phone)
        mTvCode = findViewById(R.id.tv_get_code)
        mTvSure = findViewById(R.id.tv_check)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        mTvCode.setOnClickListener {
            mPresenter.getCode(RequestInterface.REQUEST_CODE_ID, mEdtPhone.text.toString())
        }
        mTvSure.setOnClickListener { mPresenter.wxLoginHasPhone(RequestInterface.REQUEST_HAS_PHONE_ID,targetId,mode,mEdtPhone.text.toString().trim(),mEdtCode.text.toString().trim())}


    }

    private fun initListener() {
        mEdtPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 11 && mEdtCode.text.toString().length == 6) {
                    mTvSure.setBackgroundResource(R.drawable.bg_check_name_border_sure)
                    mTvSure.isEnabled = true
                } else {
                    mTvSure.setBackgroundResource(R.drawable.bg_check_name_border)
                    mTvSure.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        mEdtCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length == 6 && mEdtPhone.text.toString().length == 11) {
                    mTvSure.setBackgroundResource(R.drawable.bg_check_name_border_sure)
                    mTvSure.isEnabled = true
                } else {
                    mTvSure.setBackgroundResource(R.drawable.bg_check_name_border)
                    mTvSure.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}