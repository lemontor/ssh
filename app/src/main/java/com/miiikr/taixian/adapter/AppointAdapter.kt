package com.miiikr.taixian.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray

class AppointAdapter(fragmentManager: FragmentManager,var fragments:SparseArray<Fragment>):FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(p0: Int): Fragment {
        return  fragments.get(p0)
    }

    override fun getCount(): Int {
        return fragments.size()
    }
}