package com.miiikr.taixian.ui.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.WebpageObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.share.WbShareHandler
import com.sina.weibo.sdk.utils.Utility
import com.ssh.net.ssh.utils.IntentUtils

class ShareActivity : BaseMvpActivity<SinglePresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        findViewById<Button>(R.id.btn_share).setOnClickListener {
              IntentUtils.toPost(this@ShareActivity)
        }
    }

}