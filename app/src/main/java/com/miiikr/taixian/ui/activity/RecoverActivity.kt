package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.ui.fragment.DeliveryFragment
import com.ssh.net.ssh.utils.IntentUtils

class RecoverActivity:BaseMvpActivity<RecoverPersenter>() {
    var productId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovers)
        productId = intent.getStringExtra("productId")
        findViewById<TextView>(R.id.tv_point).setOnClickListener {
            IntentUtils.toLocation(this,productId)
        }
        findViewById<TextView>(R.id.tv_sf).setOnClickListener {
            showRightFragment(DeliveryFragment())
        }

        findViewById<TextView>(R.id.tv_find).setOnClickListener {
            IntentUtils.toRecoverStore(this)
        }
        findViewById<ImageView>(R.id.iv_back).setOnClickListener {
            finish()
        }
    }

    fun showRightFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_content, fragment)
        transaction.commit()
    }






}