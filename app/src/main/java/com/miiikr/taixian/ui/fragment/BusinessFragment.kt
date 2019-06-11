package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R

class BusinessFragment : BaseMvpFragment<PersonPresenter>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_business, null)
        contentView.findViewById<View>(R.id.iv_back).setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
        val tv = contentView.findViewById<TextView>(R.id.iv_person)
        tv.text = resources.getString(R.string.wallet_details)
        tv.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_content, MoneyDetailsFragment())
            transaction.commit()
        }
        return contentView
    }


}