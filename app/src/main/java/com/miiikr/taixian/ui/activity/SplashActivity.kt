package com.miiikr.taixian.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.activity.PermissionActivity
import com.miiikr.taixian.permission.PermissionChecker
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.FrameAnimation
import com.ssh.net.ssh.utils.IntentUtils

class SplashActivity : BaseMvpActivity<MainPresenter>() {

    var permissions: Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)

    val REQUEST_CODE: Int = 1001

    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when {
                msg!!.what == 1 -> IntentUtils.toMain(this@SplashActivity)
            }
        }
    }

    lateinit var ivAnim: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mPresenter = MainPresenter()
        val layout = findViewById<RelativeLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        ivAnim = findViewById(R.id.iv_anim)
        var permissionChecker = PermissionChecker(this)
        if (permissionChecker.lacksPermissions(permissions)) {
            PermissionActivity.startActivityForResult(this, REQUEST_CODE, permissions)
        } else {
            animation()
        }
    }

    fun animation() {
        var frameAnimation = FrameAnimation(ivAnim, mPresenter.getRes(this), 80, false)
        frameAnimation.setAnimationListener(object : FrameAnimation.AnimationListener {
            override fun onAnimationStart() {}
            override fun onAnimationEnd() {
                mHandler.sendEmptyMessageDelayed(1, 500)
            }
            override fun onAnimationRepeat() {}
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED) {
            finish()
        } else if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_GRANTED) {
            animation()
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}