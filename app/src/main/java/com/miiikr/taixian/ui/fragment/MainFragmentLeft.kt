package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.MessageEvent
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import org.greenrobot.eventbus.EventBus

class MainFragmentLeft : BaseMvpFragment<MainPresenter>() {

    lateinit var mIvBack: ImageView
    lateinit var mTvSell: TextView
    lateinit var mTvPrice: TextView

    lateinit var mTvStory: TextView
    lateinit var mTvConne: TextView
    lateinit var mTvNotify: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_main_left, null)
        initUI(contentView)
        initListener()
        return contentView
    }

    private fun initListener() {
        mTvSell.setOnClickListener {
               IntentUtils.toType(activity!!)
        }
        mTvPrice.setOnClickListener {
            IntentUtils.toType(activity!!)
        }
        mIvBack.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_LEFT_OPEN_CLOSE))
        }
        mTvStory.setOnClickListener {
            IntentUtils.toMap(activity!!)
        }
        mTvConne.setOnClickListener {
            IntentUtils.toConn(activity!!)

        }

    }

    private fun initUI(contentView: View) {
        mIvBack = contentView.findViewById(R.id.iv_left_back)
        mTvSell = contentView.findViewById(R.id.tv_sell)
        mTvPrice = contentView.findViewById(R.id.tv_price)
        mTvStory = contentView.findViewById(R.id.tv_find)
        mTvConne = contentView.findViewById(R.id.tv_conn)
        mTvNotify = contentView.findViewById(R.id.tv_notify)
    }


}