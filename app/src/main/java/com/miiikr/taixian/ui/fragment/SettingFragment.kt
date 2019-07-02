package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.entity.MessageEvent
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import org.greenrobot.eventbus.EventBus
import com.hyphenate.EMCallBack
import com.hyphenate.chat.EMClient
import com.miiikr.taixian.ui.activity.MainActivity


class SettingFragment : BaseMvpFragment<AccountPresenter>(), View.OnClickListener, AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_USER_ID) {
            val user = response as? LoginEntity.UserData
            if (user != null) {
                GlideHelper.loadBitmpaByCircleInRes(activity!!, mIvHead, R.mipmap.icon_pic_man)
                mTvName.text = user.nickName!!.trim()
                mTvPhone.text = user.phone
                mTvId.text = user.userId
                if (user.headPortrait == null || user.headPortrait.equals("")) {
                    if (user.sex == 1) {
                        GlideHelper.loadBitmpaByCircleInRes(activity!!, mIvHead, R.mipmap.icon_pic_man)
                    } else {
                        GlideHelper.loadBitmpaByCircleInRes(activity!!, mIvHead, R.mipmap.icon_pic_women)
                    }
                } else {
                    headUrl = user.headPortrait!!
                    GlideHelper.loadBitmpaByCircle(activity!!, mIvHead, user.headPortrait!!)
                }
                sex = user.sex
                if (user.sex == 1) {
                    mTvId.text = "男"
                } else {
                    mTvId.text = "女"
                }
            }
        } else if (responseId == RequestInterface.REQUEST_GET_USERINFO_ID) {
            val user = response as? LoginEntity
            if (user != null && user.state == 1) {
                var data = user.data
                if (data != null) {
                    val share = SharedPreferenceUtils(activity!!)
                    if (data.headPortrait == null || data.headPortrait.equals("")) {
                        if (data.sex == 1) {
                            GlideHelper.loadBitmpaByCircleInRes(activity!!, mIvHead, R.mipmap.icon_pic_man)
                        } else {
                            GlideHelper.loadBitmpaByCircleInRes(activity!!, mIvHead, R.mipmap.icon_pic_women)
                        }
                    } else {
                        headUrl = data.headPortrait!!
                        GlideHelper.loadBitmpaByCircle(activity!!, mIvHead, data.headPortrait!!)
                        share.putValue(SharedPreferenceUtils.PREFERENCE_U_H, data.headPortrait)
                    }
                    sex = data.sex
                    if (data.sex == 1) {
                        mTvId.text = "男"
                    } else {
                        mTvId.text = "女"
                    }
                    if (data.nickName != null) {
                        mTvName.text = data.nickName!!.trim()
                        share.putValue(SharedPreferenceUtils.PREFERENCE_U_N, data.nickName)
                    }
                    if (data.phone != null) {
                        mTvPhone.text = data.phone
                        share.putValue(SharedPreferenceUtils.PREFERENCE_U_P, data.phone)
                    }
                    share.putValueForInt(SharedPreferenceUtils.PREFERENCE_U_S, data.sex)

                }
            } else {
                mPresenter.getUserInfo(RequestInterface.REQUEST_USER_ID, activity!!)
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
        if (responseId == RequestInterface.REQUEST_GET_USERINFO_ID) {
            mPresenter.getUserInfo(RequestInterface.REQUEST_USER_ID, activity!!)
        }
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_name -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_NAME))
            v!!.id == R.id.layout_phone -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_PHONE))
            v!!.id == R.id.iv_cancel -> {
                activity!!.supportFragmentManager.popBackStack()
            }
            v!!.id == R.id.layout_head -> IntentUtils.toPic(activity!!, sex, headUrl)
            v!!.id == R.id.tv_out -> {
                mPresenter.clearValues(activity!!)
                out()

            }
        }
    }

    fun out() {
        EMClient.getInstance().logout(true, object : EMCallBack {

            override fun onSuccess() {
                activity!!.supportFragmentManager.popBackStack()
            }

            override fun onProgress(progress: Int, status: String) {

            }

            override fun onError(code: Int, message: String) {
                Log.e("tag_onError","$message")
                activity!!.supportFragmentManager.popBackStack()

            }
        })
    }


    lateinit var mLayoutName: LinearLayout
    lateinit var mLayoutPhone: LinearLayout
    lateinit var mLayoutHead: LinearLayout
    lateinit var mIvHead: ImageView
    lateinit var mIvCancel: ImageView
    lateinit var mTvName: TextView
    lateinit var mTvPhone: TextView
    lateinit var mTvId: TextView
    lateinit var mTvOut: TextView
    var sex = 0
    var headUrl = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_setting, null)
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
        initUI(contentView)
        initData()
        return contentView
    }

    private fun initData() {
        mPresenter.getUserInfo(RequestInterface.REQUEST_GET_USERINFO_ID, SharedPreferenceUtils(activity!!).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
    }


    fun initUI(contentView: View) {
        mLayoutName = contentView.findViewById(R.id.layout_name)
        mLayoutPhone = contentView.findViewById(R.id.layout_phone)
        mIvHead = contentView.findViewById(R.id.iv_head)
        mTvName = contentView.findViewById(R.id.tv_name)
        mTvPhone = contentView.findViewById(R.id.tv_phone)
        mTvId = contentView.findViewById(R.id.tv_id)
        mIvCancel = contentView.findViewById(R.id.iv_cancel)
        mLayoutHead = contentView.findViewById(R.id.layout_head)
        mTvOut = contentView.findViewById(R.id.tv_out)
        mLayoutName.setOnClickListener(this)
        mLayoutPhone.setOnClickListener(this)
        mIvCancel.setOnClickListener(this)
        mLayoutHead.setOnClickListener(this)
        mTvOut.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
                if (keyEvent.action === KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    activity!!.supportFragmentManager.popBackStack()
                    return true
                }
                return false
            }
        })
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}