package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.SparseArray
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.DetailsPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.AppointAdapter
import com.miiikr.taixian.ui.fragment.AppointFragment
import com.miiikr.taixian.widget.AppointmentPageTransformer

class AppointmentActivity:BaseMvpActivity<DetailsPresenter>() {

    lateinit var mVPAppoint:ViewPager
    lateinit var mFragments:SparseArray<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appoint)
        initUI()
        initData()
    }

    private fun initData() {
        mFragments = SparseArray()
        for(index in 0 until 3){
            mFragments.put(index,AppointFragment.newInstance())
        }
        mVPAppoint.offscreenPageLimit = 3
        mVPAppoint.setPageTransformer(false,AppointmentPageTransformer())
        mVPAppoint.adapter = AppointAdapter(supportFragmentManager,mFragments)
    }

    private fun initUI() {
        mVPAppoint = findViewById(R.id.vp_appoint)
    }


}