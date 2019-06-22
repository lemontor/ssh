package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.meng.viewpagercard_master.OnPageSelectListener
import com.miiikr.taixian.BaseMvp.IView.RecoverView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.GemmologistEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.CoverFlowViewPager
import com.ssh.net.ssh.utils.GlideHelper

class StoreRecoverActivity : BaseMvpActivity<RecoverPersenter>(), RecoverView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (RequestInterface.REQUEST_RECOVER_GEM_ID == responseId) {
            val result = response as? GemmologistEntity
            if (result != null) {
                if (result.state == 1) {
                    if (result.data != null && result.data.size != 0) {
                        datas.addAll(result.data)
                        var views = ArrayList<View>()
                        for (index in result.data) {
                            val contentView = View.inflate(this, R.layout.item_subscribe_pic, null)
                            val mIvPic = contentView.findViewById<ImageView>(R.id.iv_pic)
                            val mTvName = contentView.findViewById<TextView>(R.id.tv_name)
                            val mLevel = contentView.findViewById<TextView>(R.id.tv_level)
                            if (index.fullBodyPicture != null) {
                                GlideHelper.loadBitmapByNormal(this, mIvPic, index.fullBodyPicture!!)
                            }
                            if (index.gemmologistName != null) {
                                mTvName.text = index.gemmologistName
                            }
                            if (index.level != null) {
                                mLevel.text = index.level
                            }
                            views.add(contentView)
                        }
                        vp.setViewList(views)
                    } else {
                        ToastUtils.toastShow(this, result.message)
                    }
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var vp: CoverFlowViewPager
    lateinit var datas: ArrayList<GemmologistEntity.GemmologistData>
    var showIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_store)
        vp = findViewById(R.id.vp_pic)
        mPresenter = RecoverPersenter()
        mPresenter.attachView(this)
        vp.setOnPageSelectListener(object : OnPageSelectListener{
            override fun select(position: Int) {

            }
        })
        datas = ArrayList()
        mPresenter.getGemmologistData(RequestInterface.REQUEST_RECOVER_GEM_ID)

    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


}