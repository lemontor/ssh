package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.BigPicAapter
import com.miiikr.taixian.entity.PicEvent
import org.greenrobot.eventbus.EventBus


class BigPicActivity : AppCompatActivity() {

    lateinit var mIvBack: ImageView
    lateinit var mVpBig: ViewPager
    var picEvent:PicEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_pic)
        initUI()
        initData()
        initListener()
    }

    private fun initListener() {
        mIvBack.setOnClickListener {
            finish()
        }
        mVpBig.adapter = BigPicAapter(supportFragmentManager,picEvent!!)
    }

    private fun initData() {
        picEvent = EventBus.getDefault().getStickyEvent(PicEvent::class.java)
        if(picEvent != null){
            EventBus.getDefault().removeStickyEvent(picEvent)
        }
    }

    private fun initUI() {
        mIvBack = findViewById(R.id.iv_back)
        mVpBig = findViewById(R.id.vp_pic)
    }

}