package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MessageEvent
import com.miiikr.taixian.utils.AndroidWorkaround
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.ScreenUtils
import org.greenrobot.eventbus.EventBus

class MainFragmentLeft : BaseMvpFragment<MainPresenter>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_main_left, null)
        initUI(contentView)
        initListener()
        return contentView
    }

    private fun initListener() {
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
                if (keyEvent.action === KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_LEFT_OPEN_CLOSE))
                    return true
                }
                return false
            }
        })
    }

    private fun initUI(contentView: View) {
        val layout = contentView!!.findViewById<RelativeLayout>(R.id.root_layout)
        layout.setPadding(0, ScreenUtils.dp2px(activity!!,50), 0, AndroidWorkaround.getNavigationBarHeight(activity!!))
        contentView.findViewById<TextView>(R.id.tv_find).setOnClickListener {  IntentUtils.toMap(activity!!) }
        contentView.findViewById<TextView>(R.id.tv_conn).setOnClickListener { IntentUtils.toConn(activity!!) }
        contentView.findViewById<TextView>(R.id.tv_notify).setOnClickListener { IntentUtils.toAdvice(activity!!) }
        contentView.findViewById<TextView>(R.id.tv_sell).setOnClickListener { IntentUtils.toType(activity!!, 1) }
        contentView.findViewById<TextView>(R.id.tv_price).setOnClickListener {  IntentUtils.toType(activity!!, 2) }
        contentView.findViewById<ImageView>(R.id.iv_left_back).setOnClickListener { EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_LEFT_OPEN_CLOSE)) }

    }


}