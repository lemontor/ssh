package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.PersonItemAdapter
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.entity.MessageEvent
import com.miiikr.taixian.ui.activity.MainActivity
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.tencent.mm.opensdk.constants.Build
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import org.greenrobot.eventbus.EventBus

class MainFragmentRight : BaseMvpFragment<MainPresenter>() {

    lateinit var mRvItem: RecyclerView
    lateinit var datas: SparseArray<String>
    lateinit var personItemAdapter: PersonItemAdapter
    lateinit var mIvCancel: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_main_right, null)
        initUI(contentView)
        initData()
        return contentView
    }

    private fun initData() {
        datas = SparseArray()
        datas.put(0, activity!!.resources.getString(R.string.main_right_notify_check))
        datas.put(1, activity!!.resources.getString(R.string.main_right_notify_sell))
        datas.put(2, activity!!.resources.getString(R.string.main_right_notify_price))
        datas.put(3, activity!!.resources.getString(R.string.main_right_notify_sub))
        datas.put(4, activity!!.resources.getString(R.string.main_right_notify_cus))
        datas.put(5, activity!!.resources.getString(R.string.main_right_notify_pack))
        datas.put(6, activity!!.resources.getString(R.string.main_right_notify_news))
        datas.put(7, activity!!.resources.getString(R.string.main_right_notify_goods))
        datas.put(8, activity!!.resources.getString(R.string.main_right_notify_question))
        datas.put(9, activity!!.resources.getString(R.string.main_right_notify_set))

        personItemAdapter = PersonItemAdapter(activity!!, datas)
        mRvItem.adapter = personItemAdapter
        personItemAdapter.setItemClickListener(object : OnClickItemListener {
            override fun clickItem(position: Int) {
                if (position == 8) {
                    IntentUtils.toQuestion(activity!!)
                } else {
                    if ((activity as MainActivity).getSharedPreferences()!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I).equals("")) {
                        IntentUtils.toLogin(activity!!)
                        return
                    }
                    when (position) {
                        0 -> IntentUtils.toCheck(activity!!, 1)
                        1 -> IntentUtils.toCheck(activity!!, 2)
                        2 -> IntentUtils.toEva(activity!!)
                        3 -> IntentUtils.toSub(activity!!)
                        4 -> IntentUtils.toChat(activity!!)
                        5 -> IntentUtils.toWallet(activity!!)
                        6 -> IntentUtils.toNews(activity!!)
                        7 -> {//分享
                            IntentUtils.toShare(activity!!)
                        }
                        8 -> IntentUtils.toQuestion(activity!!)
                        else -> {
                            EventBus.getDefault().post(MessageEvent(position))
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(view: View, i: Int, keyEvent: KeyEvent): Boolean {
                if (keyEvent.action === KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_CLOSE))
                    return true
                }
                return false
            }
        })
    }


    fun share() {
        var textObj = WXTextObject()
        textObj.text = "测试分享文字"

        var msg = WXMediaMessage()
        msg.mediaObject = textObj
        msg.description = "测描述字段"

        var req = SendMessageToWX.Req()
        req.transaction = System.currentTimeMillis().toString()
        req.message = msg
        if (SSHApplication.instance.api!!.wxAppSupportAPI >= Build.TIMELINE_SUPPORTED_SDK_INT) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline
            SSHApplication.instance.api!!.sendReq(req)
        } else {
            req.scene = WXSceneSession
            SSHApplication.instance.api!!.sendReq(req)
        }
    }


    private fun initUI(contentView: View?) {
        val layout = contentView!!.findViewById<LinearLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(activity!!))
        mRvItem = contentView!!.findViewById(R.id.rv_item)
        mIvCancel = contentView!!.findViewById(R.id.iv_cancel)
        var linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvItem.layoutManager = linearLayoutManager
        mIvCancel.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(AppConfig.FRAGMENT_RIGHT_OPEN_CLOSE))
        }
    }

}