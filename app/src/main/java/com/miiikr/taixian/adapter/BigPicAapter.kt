package com.miiikr.taixian.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log

import com.miiikr.taixian.entity.Bean
import com.miiikr.taixian.entity.PicEntity
import com.miiikr.taixian.entity.PicEvent
import com.miiikr.taixian.ui.fragment.BigPicFragment
import com.miiikr.taixian.ui.fragment.PlaceholderFragment
import com.miiikr.taixian.widget.CustomViewPager
import com.miiikr.taixian.widget.MainViewPager
import java.io.File

import java.util.ArrayList

class BigPicAapter(fm: FragmentManager, var picEntity: PicEvent) : FragmentStatePagerAdapter(fm) {
    var mPlaceholderFragment: BigPicFragment = BigPicFragment()
    var mFragments: MutableList<Fragment> = ArrayList()

    init {
        for (position in 0 until picEntity.pics.size) {
        }

        picEntity.pics!!.forEach {
            //            Log.e("tag_map","key:${it.key} value:${it.value}")
            mFragments.add(mPlaceholderFragment.newInstance(it.value.absolutePath, it.key + 1))
        }

    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }


}
