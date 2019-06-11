package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.MoneyDetailsAdapter
import com.miiikr.taixian.widget.slide.PlusItemSlideCallback
import com.miiikr.taixian.widget.slide.WItemTouchHelperPlus

class MoneyDetailsFragment:BaseMvpFragment<PersonPresenter>() {

    lateinit var mIvBack:ImageView
    lateinit var mTvTitle:TextView
    lateinit var mRvMoney:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.activity_subscribe,null)
        initUI(contentView)
        initListener()
        return contentView
    }

    private fun initListener() {
        mIvBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
    }

    private fun initUI(contentView: View?) {
        mIvBack = contentView!!.findViewById(R.id.iv_back)
        mTvTitle = contentView!!.findViewById(R.id.tv_title)
        mTvTitle.text = resources.getString(R.string.wallet_details)
        mRvMoney=contentView!!.findViewById(R.id.rv_sub)

        val linearLayoutManager = LinearLayoutManager(activity!!)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvMoney.layoutManager = linearLayoutManager
//        mRvMoney.addItemDecoration(RecycleViewDivider(
//                activity!!, LinearLayoutManager.HORIZONTAL, ScreenUtils.dp2px(activity!!, 10), resources.getColor(R.color.color_F2F2F2)))
        mRvMoney.adapter = MoneyDetailsAdapter(activity!!)
        val callback = PlusItemSlideCallback()
        val extension = WItemTouchHelperPlus(callback)
        extension.attachToRecyclerView(mRvMoney)


    }


}