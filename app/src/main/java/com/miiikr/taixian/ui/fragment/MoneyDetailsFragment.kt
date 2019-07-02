package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.BaseMvp.presenter.RewardPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.MoneyDetailsAdapter
import com.miiikr.taixian.entity.CashDetailsEntity
import com.miiikr.taixian.ui.activity.WalletActivity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.slide.PlusItemSlideCallback
import com.miiikr.taixian.widget.slide.WItemTouchHelperPlus

class MoneyDetailsFragment : BaseMvpFragment<RewardPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (RequestInterface.REQUEST_CASH_DETAILS_LIST_ID == responseId) {
            val result = response as? CashDetailsEntity
            if (result != null) {
                if (result.state == 1) {
                    if (result.data != null && result.data!!.size > 0) {
                        data.addAll(result.data!!)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    ToastUtils.toastShow(activity!!, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mIvBack: ImageView
    lateinit var mTvTitle: TextView
    lateinit var mRvMoney: RecyclerView
    lateinit var data: ArrayList<CashDetailsEntity.CashListEntity>
    lateinit var adapter: MoneyDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = RewardPresenter()
        mPresenter.attachView(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.activity_subscribe, null)
        initUI(contentView)
        initListener()
        return contentView
    }

    private fun initListener() {
        mIvBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
//        mPresenter.getCashListDetails(RequestInterface.REQUEST_CASH_DETAILS_LIST_ID, (activity as WalletActivity).sharedPreferenceUtils!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
        mPresenter.getCashListDetails(RequestInterface.REQUEST_CASH_DETAILS_LIST_ID, "924f72b105074b43b085458489acbe27")

    }

    private fun initUI(contentView: View?) {
        mIvBack = contentView!!.findViewById(R.id.iv_back)
        mTvTitle = contentView!!.findViewById(R.id.tv_title)
        mTvTitle.text = resources.getString(R.string.wallet_details)
        mRvMoney = contentView!!.findViewById(R.id.rv_sub)

        val linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvMoney.layoutManager = linearLayoutManager
        data = ArrayList()
        adapter = MoneyDetailsAdapter(activity!!, data)
        mRvMoney.adapter = adapter
        val callback = PlusItemSlideCallback()
        val extension = WItemTouchHelperPlus(callback)
        extension.attachToRecyclerView(mRvMoney)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }


}