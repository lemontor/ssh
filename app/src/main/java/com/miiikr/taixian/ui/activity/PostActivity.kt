package com.miiikr.taixian.ui.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.utils.RequestInterface
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.api.ImageObject
import com.sina.weibo.sdk.api.WebpageObject
import com.sina.weibo.sdk.api.WeiboMultiMessage
import com.sina.weibo.sdk.auth.AuthInfo
import com.sina.weibo.sdk.share.WbShareHandler
import com.sina.weibo.sdk.utils.Utility
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.ScreenUtils
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXImageObject
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage

class PostActivity : BaseMvpActivity<SinglePresenter>(), MainView {

    var shareHandler: WbShareHandler? = null

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_QR_ID) {
            val resultBitmap = response as? Bitmap
            if (resultBitmap != null) {
                mIvQR.setImageBitmap(resultBitmap)
            }
        } else if (responseId == RequestInterface.REQUEST_USER_ID) {
            val resultUser = response as? LoginEntity.UserData
            if (resultUser != null) {
                userId = resultUser.userId
                phone = resultUser.phone
                mTvName.text = if (resultUser.nickName.equals("")) resultUser.phone else resultUser.nickName
                GlideHelper.loadBitmpaByCircle(this@PostActivity, mIvHead, "http://gl.baidu.com/gonglve/api/getcontent/?doc_id=38a05650ac02de80d4d8d15abe23482fb4da02bb&type=pic&src=7fa6b432dc3048f50f19abfe9ccd2ce5.jpg")
            }
        } else if (responseId == RequestInterface.REQUEST_VIEW_ID) {
            val resultBitmap = response as? Bitmap
            if (resultBitmap != null) {
                when (clickIndex) {
                    1 ->shareToWx(1, resultBitmap)
                    2 ->shareToWx(2, resultBitmap)
                    3 ->shareToWb(resultBitmap,share_url)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {

    }

    var share_url = "https://www.baidu.com/#/courseDetail?userId="
    var userId: String? = ""
    var phone: String? = ""

    lateinit var mIvQR: ImageView
    lateinit var mIvHead: ImageView
    lateinit var mIvBack: ImageView
    lateinit var mIvWXFriend: TextView
    lateinit var mIvWXCircle: TextView
    lateinit var mIvWB: TextView
    lateinit var mTvName: TextView
    lateinit var mLayoutContent: LinearLayout
    var clickIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        mPresenter = SinglePresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
    }

    private fun initData() {
        share_url = "$share_url$userId&phone=$phone"
        mPresenter.createQR(RequestInterface.REQUEST_QR_ID, share_url, ScreenUtils.dp2px(this, 180), ScreenUtils.dp2px(this, 180))
        mPresenter.getUserInfo(RequestInterface.REQUEST_USER_ID, this)
    }

    private fun initUI() {
        mIvQR = findViewById(R.id.iv_qr)
        mIvHead = findViewById(R.id.iv_head)
        mIvBack = findViewById(R.id.iv_back)
        mIvWXFriend = findViewById(R.id.tv_wx_friend)
        mIvWXCircle = findViewById(R.id.tv_wx_circle)
        mIvWB = findViewById(R.id.tv_wb)
        mTvName = findViewById(R.id.tv_name)
        mLayoutContent = findViewById(R.id.layout_content)

        mIvWXFriend.setOnClickListener {
            clickIndex = 1
            mPresenter.getView(RequestInterface.REQUEST_VIEW_ID, mLayoutContent)
        }
        mIvWXCircle.setOnClickListener {
            clickIndex = 2
            mPresenter.getView(RequestInterface.REQUEST_VIEW_ID, mLayoutContent)
        }
        mIvWB.setOnClickListener {
            clickIndex = 3
            mPresenter.getView(RequestInterface.REQUEST_VIEW_ID, mLayoutContent)
        }
        Looper.myQueue().addIdleHandler {
            WbSdk.install(this@PostActivity, AuthInfo(this@PostActivity, AppConfig.WB_KEY_APP, AppConfig.REDIRECT_URL, AppConfig.SCOPE))
            shareHandler = WbShareHandler(this@PostActivity)
            shareHandler!!.registerApp()
            false // false表示只监听一次idle事件，之后就不会再执行这个函数了
        }
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


    fun shareToWx(type: Int, bitmap: Bitmap) {
        var imgObj = WXImageObject(bitmap)
        val msg = WXMediaMessage()
        msg.mediaObject = imgObj

        var req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        if (type == 2) { //朋友圈
            req.scene = SendMessageToWX.Req.WXSceneTimeline
        } else if (type == 1) {
            req.scene = SendMessageToWX.Req.WXSceneSession
        }
        SSHApplication.instance.api!!.sendReq(req)
    }

    fun shareToWb(bitmap: Bitmap, url: String) {
        var wbMessage = WeiboMultiMessage()
        wbMessage.mediaObject = getWebPageObj(bitmap, url)
        wbMessage.imageObject = getImageObj(bitmap)
        shareHandler!!.shareMessage(wbMessage, false)
    }


    fun getWebPageObj(bitmap: Bitmap, url: String): WebpageObject {
        var obj = WebpageObject()
        obj.identify = Utility.generateGUID()
        obj.title = "测试title"
        obj.description = "测试描述"
        obj.setThumbImage(bitmap)
        obj.actionUrl = url
        obj.defaultText = "webpage默认文案"
        return obj
    }

    private fun getImageObj(bitmap: Bitmap): ImageObject {
        val imageObject = ImageObject()
        imageObject.setImageObject(bitmap)
        return imageObject
    }






}