package com.miiikr.taixian.ui.fragment

import android.graphics.BitmapFactory
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
import com.miiikr.taixian.widget.ZoomImageView
import com.ssh.net.ssh.utils.GlideHelper

class BigPicFragment : BaseMvpFragment<MainPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = arguments!!.getString(BIG_PIC_PATH)
        position = arguments!!.getInt(BIG_PIC_INDEX)
    }

    var path: String? = null
    var position: Int = 0
    val BIG_PIC_PATH = "pic"
    val BIG_PIC_INDEX = "index"


    fun newInstance(path: String, position: Int): BigPicFragment {
        val fragment = BigPicFragment()
        val args = Bundle()
        args.putString(BIG_PIC_PATH, path)
        args.putInt(BIG_PIC_INDEX, position)
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_big_pic, null)
        val img = rootView.findViewById<ZoomImageView>(R.id.iv_pic)
        val tvIndex = rootView.findViewById<TextView>(R.id.tv_index)
        tvIndex.text = "$position"
        img.setImageBitmap(BitmapFactory.decodeFile(path))
        return rootView
    }


}