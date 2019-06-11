package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.Bean
import com.ssh.net.ssh.utils.GlideHelper

class PlaceholderFragment : BaseMvpFragment<MainPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databean = arguments!!.getSerializable(ARG_SECTION_DATA) as Bean
        position = arguments!!.getInt(ARG_SECTION_NUMBER)
    }

    var databean: Bean? = null
    var position: Int = 0
    private val ARG_SECTION_DATA = "section_data"
    private val ARG_SECTION_NUMBER = "section_number"

    fun newInstance(databean: Bean, position: Int): PlaceholderFragment {
        val fragment = PlaceholderFragment()
        val args = Bundle()
        args.putSerializable(ARG_SECTION_DATA, databean)
        args.putSerializable(ARG_SECTION_NUMBER, position)
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main_pic, null)
        val tv_title = rootView.findViewById(R.id.tv_title) as TextView
        tv_title.text = databean!!.title
        val tv_title2 = rootView.findViewById(R.id.tv_title2) as TextView
//        val tv_sign = rootView.findViewById(R.id.tv_sign) as TextView
//        tv_sign.text = position.toString()
        tv_title2.visibility = View.VISIBLE
        tv_title2.text = databean!!.title2
        val iv_picture = rootView.findViewById(R.id.iv_picture) as ImageView
        GlideHelper.loadBitmapByNormal2(activity, iv_picture, databean!!.url!!)
//        GlideHelper.compressPicByLuban(activity!!,iv_picture,databean!!.url!!)
        return rootView
    }


}