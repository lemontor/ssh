package com.miiikr.taixian.wxapi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.utils.ToastUtils
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler

class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SSHApplication.instance.api!!.handleIntent(intent, this)
    }


    override fun onResp(p0: BaseResp?) {
        when (p0!!.errCode) {
            BaseResp.ErrCode.ERR_OK -> ToastUtils.toastShow(this, "分享成功")
            BaseResp.ErrCode.ERR_USER_CANCEL -> ToastUtils.toastShow(this, "分享取消")
            BaseResp.ErrCode.ERR_AUTH_DENIED -> ToastUtils.toastShow(this, "分享拒绝")
            else -> if (p0.type == ConstantsAPI.COMMAND_SENDAUTH) {
                ToastUtils.toastShow(this, "微信授权取消")
            } else {
                ToastUtils.toastShow(this, "分享取消")
            }
        }
        finish()
    }

    override fun onReq(p0: BaseReq?) {
    }
}