package com.miiikr.taixian.ui.activity

import android.content.Intent
import android.os.Bundle
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.ui.fragment.ImageGridFragment

class VideoThumbActivity :BaseMvpActivity<SinglePresenter>() {
    private val TAG = "ImageGridActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentByTag(TAG) == null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.add(android.R.id.content, ImageGridFragment(), TAG)
            ft.commit()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

}