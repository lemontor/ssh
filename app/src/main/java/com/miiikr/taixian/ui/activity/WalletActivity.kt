package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.BaseMvp.presenter.RewardPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MoneyCompleteEntity
import com.miiikr.taixian.ui.fragment.BusinessFragment
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.SpannableUtils

class WalletActivity : BaseMvpActivity<RewardPresenter>(),AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_MONEY_FOR_COMPELETE_ID){
            val result =  response as? MoneyCompleteEntity
            if(result != null){
                if(result.state == 1 && result.data != null){
                    tvFinished.text ="￥${result.data.money}\n已成单红包 >"
                }else{
                    ToastUtils.toastShow(this,result.message)
                }
            }
        }else if(responseId == RequestInterface.REQUEST_MONEY_FOR_WAITE_ID){
            val result =  response as? MoneyCompleteEntity
            if(result != null){
                if(result.state == 1 && result.data != null){
                    tvUnFinished.text ="￥${result.data.money}\n未成单红包 >"
                }else{
                    ToastUtils.toastShow(this,result.message)
                }
            }
        }

    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var tvFinished: TextView
    lateinit var tvUnFinished: TextView
    lateinit var tvYue: TextView
    lateinit var tvActive: TextView
    var mFragmentManager: FragmentManager = supportFragmentManager
    var sharedPreferenceUtils:SharedPreferenceUtils?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        mPresenter = RewardPresenter()
        mPresenter.attachView(this)
        initUI()
        initListener()
    }

    private fun initListener() {
        sharedPreferenceUtils = SharedPreferenceUtils(this)
        tvFinished.setOnClickListener { IntentUtils.toMoneyState(this,2) }
        tvUnFinished.setOnClickListener { IntentUtils.toMoneyState(this,3) }
        tvYue.setOnClickListener {
            showContentFragment(BusinessFragment())
        }
        tvActive.setOnClickListener { }
//        mPresenter.getMoneyForComplete(RequestInterface.REQUEST_MONEY_FOR_COMPELETE_ID,SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
//        mPresenter.getMoneyForWait(RequestInterface.REQUEST_MONEY_FOR_COMPELETE_ID,SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
        mPresenter.getMoneyForComplete(RequestInterface.REQUEST_MONEY_FOR_COMPELETE_ID,"924f72b105074b43b085458489acbe27")
        mPresenter.getMoneyForWait(RequestInterface.REQUEST_MONEY_FOR_WAITE_ID,"924f72b105074b43b085458489acbe27")

    }

    private fun initUI() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        tvFinished = findViewById(R.id.tv_money)
        tvUnFinished = findViewById(R.id.tv_money_no)
        tvYue = findViewById(R.id.tv_yue)
        tvActive = findViewById(R.id.tv_active)
        SpannableUtils.setTextSize(tvFinished)
        SpannableUtils.setTextSize2(tvUnFinished)
    }


    fun showContentFragment(fragment: Fragment) {
        val transaction = mFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_content, fragment)
        transaction.commit()
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }





}