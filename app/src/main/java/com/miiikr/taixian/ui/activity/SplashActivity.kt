package com.miiikr.taixian.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.activity.PermissionActivity
import com.miiikr.taixian.permission.PermissionChecker
import com.ssh.net.ssh.utils.IntentUtils

class SplashActivity:BaseMvpActivity<MainPresenter>() {
    var permissions:Array<String> = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_COARSE_LOCATION)

    val REQUEST_CODE:Int = 1001

    var mHandler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when{
                msg!!.what == 1 -> IntentUtils.toMain(this@SplashActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var permissionChecker = PermissionChecker(this)
        if(permissionChecker.lacksPermissions(permissions)){
            PermissionActivity.startActivityForResult(this,REQUEST_CODE,permissions)
        }else{
            mHandler.sendEmptyMessageDelayed(1,1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode ==  REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_DENIED){
            finish()
        }else if(requestCode ==  REQUEST_CODE && resultCode == PermissionActivity.PERMISSIONS_GRANTED){
            mHandler.sendEmptyMessageDelayed(1,1000)
        }
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }


}