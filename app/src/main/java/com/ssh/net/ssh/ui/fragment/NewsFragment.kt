package com.ssh.net.ssh.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ssh.net.ssh.R

class NewsFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_news,null,false)
        val ivBack = contentView.findViewById<View>(R.id.iv_back)
        ivBack.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
        return contentView
    }

}