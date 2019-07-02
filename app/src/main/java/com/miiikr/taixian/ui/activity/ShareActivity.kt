package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MoneyEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.IntentUtils

class ShareActivity : BaseMvpActivity<SinglePresenter>(), MainView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        val result = response as? MoneyEntity
        if (result != null) {
            if (result.state == 1) {
                if (result.state != null) {
                    mTvCount.text = "${result.data.inviteNumber}"
                    mTvAll.text = "${result.data.cashBonusesSum}"
                    mTvNow.text = "${result.data.bonuses}"
                }
            } else {
                ToastUtils.toastShow(this, result.message)
            }
        }

    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mTvCount: TextView
    lateinit var mTvNow: TextView
    lateinit var mTvAll: TextView
    lateinit var mIvBack: ImageView
    lateinit var mSSHProgressHUD: SSHProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        mPresenter = SinglePresenter()
        mPresenter.attachView(this)
        initUI()
//        mPresenter.getMoneyData(RequestInterface.REQUEST_MONEY_DATA_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
        mPresenter.getMoneyData(RequestInterface.REQUEST_MONEY_DATA_ID, "924f72b105074b43b085458489acbe27")


    }

    private fun initUI() {
        mTvCount = findViewById(R.id.tv_num_value)
        mTvNow = findViewById(R.id.tv_money_get_value)
        mTvAll = findViewById(R.id.tv_money_all_value)
        mSSHProgressHUD = SSHProgressHUD.getInstance(this@ShareActivity)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
        findViewById<Button>(R.id.btn_share).setOnClickListener {
            IntentUtils.toPost(this@ShareActivity)
        }
        mIvBack = findViewById(R.id.iv_back)
        mIvBack.setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.tv_reward).setOnClickListener { IntentUtils.toMoneyState(this,1) }
    }

    override fun hideLoading() {
        super.hideLoading()
        mSSHProgressHUD.dismiss()
    }

    override fun showLoading() {
        super.showLoading()
        mSSHProgressHUD.show()
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}