package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.ui.fragment.BusinessFragment
import com.ssh.net.ssh.utils.SpannableUtils

class WalletActivity : BaseMvpActivity<PersonPresenter>() {
    lateinit var tvFinished: TextView
    lateinit var tvUnFinished: TextView
    lateinit var tvYue: TextView
    lateinit var tvActive: TextView
    var mFragmentManager: FragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        initUI()
        initListener()
    }

    private fun initListener() {
        tvYue.setOnClickListener {
            showContentFragment(BusinessFragment())
        }
        tvActive.setOnClickListener { }
    }

    private fun initUI() {
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


}