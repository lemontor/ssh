package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R

class SubscriberFragment:BaseMvpFragment<RecoverPersenter>() {

    lateinit var rootLayout:LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.item_subscribe_pic,null)
        rootLayout = contentView.findViewById(R.id.layout_root)
        return contentView
    }

    fun getLayout():LinearLayout{
        return rootLayout
    }


}