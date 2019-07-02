package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.RewardPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.MoneyStateAdapter
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.MoneyStateEntity
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.ssh.net.ssh.utils.ScreenUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider


class MoneyStateActivity : BaseMvpActivity<RewardPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_MONEY_TO_PACKAGE_ID) {
            val result = response  as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    datas[clickIndex].state = 2
                    adapter.notifyItemChanged(clickIndex)
                    ToastUtils.toastShow(this, "领取红包成功")//成功做弹窗
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }else if(responseId == RequestInterface.REQUEST_CASH_TO_PACKAGE_ID){
            val result = response  as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    datas[clickIndex].state = 2
                    adapter.notifyItemChanged(clickIndex)
                    ToastUtils.toastShow(this, "领取红包成功")
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }
        else {
            val result = response  as? MoneyStateEntity
            if (result != null) {
                if (result.state == 1) {
                    if (result.data != null && result.data!!.size > 0) {
                        datas.addAll(result.data!!)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mRvState: RecyclerView
    lateinit var mTvTitle: TextView
    lateinit var datas: ArrayList<MoneyStateEntity.MoneyStateDetailsEntity>
    lateinit var adapter: MoneyStateAdapter
    var from = 0
    var clickIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_state)
        val layout = findViewById<LinearLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        mPresenter = RewardPresenter()
        mPresenter.attachView(this)
        initUI()
        initOBJ()
    }

    private fun initOBJ() {
        datas = ArrayList()
        from = intent.getIntExtra("from", 0)
        when (from) {
            3 -> mTvTitle.text = "未成单红包"
            else -> mTvTitle.text = "已成单红包"
        }
        adapter = MoneyStateAdapter(this, datas, from, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                clickIndex = position
                if (from == 1) {
                    mPresenter.getMoney(RequestInterface.REQUEST_MONEY_TO_PACKAGE_ID, "924f72b105074b43b085458489acbe27", datas[position].bonusesId)
//              mPresenter.getMoney(RequestInterface.REQUEST_MONEY_TO_PACKAGE_ID, SharedPreferenceUtils(this@MoneyStateActivity).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!,datas[position].bonusesId)
                } else if (from == 2) {
                    mPresenter.getCash(RequestInterface.REQUEST_CASH_TO_PACKAGE_ID, "924f72b105074b43b085458489acbe27", datas[position].bonusesId)
//              mPresenter.getCash(RequestInterface.REQUEST_MONEY_TO_PACKAGE_ID, SharedPreferenceUtils(this@MoneyStateActivity).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!,datas[position].bonusesId)
                }
            }
        })
        mRvState.adapter = adapter
        when (from) {
            //mPresenter.getMoneyData(RequestInterface.REQUEST_MONEY_STATE_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
            1 -> mPresenter.getMoneyData(RequestInterface.REQUEST_MONEY_STATE_ID, "924f72b105074b43b085458489acbe27")//从分享页面跳入
            //mPresenter.getMoneyCompleteList(RequestInterface.REQUEST_MONEY_FOR_COMPELETE_LIST_ID,SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
            2 -> mPresenter.getMoneyCompleteList(RequestInterface.REQUEST_MONEY_FOR_COMPELETE_LIST_ID, "924f72b105074b43b085458489acbe27")
            //mPresenter.getMoneyWaiteList(RequestInterface.REQUEST_MONEY_FOR_WAITE_LIST_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
            3 -> mPresenter.getMoneyWaiteList(RequestInterface.REQUEST_MONEY_FOR_WAITE_LIST_ID, "924f72b105074b43b085458489acbe27")
        }

    }

    private fun initUI() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        mRvState = findViewById(R.id.rv_state)
        mTvTitle = findViewById(R.id.tv_title)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvState.layoutManager = linearLayoutManager
        mRvState.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, ScreenUtils.dp2px(this, 18), resources.getColor(R.color.color_ffffff)))
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


}