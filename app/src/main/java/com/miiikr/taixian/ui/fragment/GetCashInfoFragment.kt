package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.RewardPresenter
import com.miiikr.taixian.R

class GetCashInfoFragment:BaseMvpFragment<RewardPresenter>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView =  inflater.inflate(R.layout.fragment_cash_info,null)
        return contentView
    }



}