package com.miiikr.taixian.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.ui.fragment.NetFragment

class ConnActivity:BaseMvpActivity<MainPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conn)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tv_phone).setOnClickListener { callPhone("400-833-1577") }
        findViewById<TextView>(R.id.tv_net).setOnClickListener { showFragment((NetFragment())) }
    }


    fun callPhone(phoneNum:String) {
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }


    fun showFragment(fragment: Fragment) {
        val bundle =  Bundle()
        bundle.putString("url","http://www.shunshihang.com/")
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_content, fragment)
        transaction.commit()
    }





}