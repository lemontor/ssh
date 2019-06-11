package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.DetailsPresenter
import com.miiikr.taixian.R

class AppointFragment:BaseMvpFragment<DetailsPresenter>() {

    companion object{
        fun newInstance():AppointFragment{
            return AppointFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_appoint,container,false)
        return contentView
    }



}