package com.ssh.net.ssh.ui.fragment

import android.accounts.Account
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ssh.net.ssh.BaseMvp.View.BaseMvpFragment
import com.ssh.net.ssh.BaseMvp.presenter.AccountPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.entity.MessageEvent
import com.ssh.net.ssh.utils.AppConfig
import org.greenrobot.eventbus.EventBus

class SettingFragment : BaseMvpFragment<AccountPresenter>(), View.OnClickListener {

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_name -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_NAME))
            v!!.id == R.id.layout_phone -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_PHONE))
            else -> {
            }
        }
    }

    lateinit var mLayoutName: LinearLayout
    lateinit var mLayoutPhone:LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_setting, null)
        initUI(contentView)
        return contentView
    }


    fun initUI(contentView: View) {
        mLayoutName = contentView.findViewById(R.id.layout_name)
        mLayoutPhone = contentView.findViewById(R.id.layout_phone)
        mLayoutName.setOnClickListener(this)
        mLayoutPhone.setOnClickListener(this)
    }


}