package com.ssh.net.ssh.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpFragment
import com.ssh.net.ssh.BaseMvp.presenter.MainPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.`interface`.OnClickItemListener
import com.ssh.net.ssh.adapter.PersonItemAdapter
import com.ssh.net.ssh.entity.MessageEvent
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus

class MainFragmentRight : BaseMvpFragment<MainPresenter>() {
    lateinit var mRvItem: RecyclerView
    lateinit var datas: SparseArray<String>
    lateinit var personItemAdapter: PersonItemAdapter
    lateinit var mIvCancel:ImageView

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
                when(position){
                    0 -> IntentUtils.toCheck(activity!!, 0)
                    1-> IntentUtils.toCheck(activity!!, 1)
                    2-> IntentUtils.toEva(activity!!)
                    3->IntentUtils.toSub(activity!!)
                    6->IntentUtils.toNews(activity!!)
                    8->IntentUtils.toQuestion(activity!!)
                    else->{
                        EventBus.getDefault().post(MessageEvent(position))
                    }
                }
            }
        })
    }

    private fun initUI(contentView: View?) {
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