package com.miiikr.taixian.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import com.miiikr.taixian.entity.Bean
import com.miiikr.taixian.ui.fragment.PlaceholderFragment
import com.miiikr.taixian.widget.CustomViewPager
import com.miiikr.taixian.widget.MainViewPager

import java.util.ArrayList

class NtAdapter2(fm: FragmentManager,var list: List<Bean>) : FragmentStatePagerAdapter(fm) {
    var mPlaceholderFragment: PlaceholderFragment = PlaceholderFragment()
    var mFragments: MutableList<Fragment> = ArrayList()

    init {
        for (position in list.indices) {
            mFragments.add(mPlaceholderFragment.newInstance(list[position], position))
        }

    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }


}
