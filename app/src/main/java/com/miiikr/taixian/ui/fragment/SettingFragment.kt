package com.miiikr.taixian.ui.fragment

import android.os.Bundle
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
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import org.greenrobot.eventbus.EventBus

class SettingFragment : BaseMvpFragment<AccountPresenter>(), View.OnClickListener, AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_USER_ID) {
            val user = response as? LoginEntity.UserData
            if (user != null) {
                GlideHelper.loadBitmpaByCircleInRes(activity!!,mIvHead,R.mipmap.icon_pic_man)
                mTvName.text = user.nickName
                mTvPhone.text = user.phone
                mTvId.text = user.userId

            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {

    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_name -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_NAME))
            v!!.id == R.id.layout_phone -> EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_NICK_PHONE))
            v!!.id == R.id.iv_cancel ->  activity!!.supportFragmentManager.popBackStack()
            v!!.id == R.id.layout_head -> IntentUtils.toPic(activity!!)
            else -> {
            }
        }
    }

    lateinit var mLayoutName: LinearLayout
    lateinit var mLayoutPhone: LinearLayout
    lateinit var mLayoutHead:LinearLayout
    lateinit var mIvHead: ImageView
    lateinit var mIvCancel: ImageView
    lateinit var mTvName: TextView
    lateinit var mTvPhone: TextView
    lateinit var mTvId: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_setting, null)
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
        initUI(contentView)
        initData()
        return contentView
    }

    private fun initData() {
        mPresenter.getUserInfo(RequestInterface.REQUEST_USER_ID,activity!!)
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
        mLayoutName.setOnClickListener(this)
        mLayoutPhone.setOnClickListener(this)
        mIvCancel.setOnClickListener(this)
        mLayoutHead.setOnClickListener(this)
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}