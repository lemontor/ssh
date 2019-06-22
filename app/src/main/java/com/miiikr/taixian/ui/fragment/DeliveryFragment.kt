package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.ui.activity.RecoverActivity

class DeliveryFragment : BaseMvpFragment<RecoverPersenter>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_write_delivery, null)
        contentView.findViewById<ImageView>(R.id.iv_back).setOnClickListener { activity!!.supportFragmentManager.popBackStack() }
        contentView.findViewById<TextView>(R.id.tv_delivery).setOnClickListener {
            (activity as RecoverActivity).showRightFragment(UploadDeliveryFragment())
        }
        contentView.findViewById<TextView>(R.id.tv_point).setOnClickListener {  }
        return contentView
    }

}