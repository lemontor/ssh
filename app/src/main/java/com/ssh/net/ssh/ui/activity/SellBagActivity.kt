package com.ssh.net.ssh.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.IView.MainView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpActivity
import com.ssh.net.ssh.BaseMvp.presenter.UpdatePresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.`interface`.OnClickItemListener
import com.ssh.net.ssh.adapter.PicAdapter
import com.ssh.net.ssh.entity.PicEntity
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.widget.*

class SellBagActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView,OnClickItemListener {
    override fun clickItem(position: Int) {
        showPhotoPopupWindow()
    }

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        when (responseId) {
            AppConfig.REQUEST_ID_GET_PIC -> {
                val picData = response as? PicEntity
                if (picData != null) {
                    mPicDatas.addAll(picData!!.picData)
                    picAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_brand -> IntentUtils.toBrand(this)
            v!!.id == R.id.layout_func -> showSizePopupWindow()
            v!!.id == R.id.layout_file -> showFilePopupWindow()
        }
    }

    lateinit var mLayoutHead: LinearLayout
    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout
    lateinit var mLayoutFunc: LinearLayout
    lateinit var mLayoutFile: LinearLayout
    lateinit var mRvPic: RecyclerView
    lateinit var mPicDatas: ArrayList<PicEntity.PicData>
    lateinit var picAdapter: PicAdapter

    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null
    var sizePopupWindow: NormalPopupWindow? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag_details)
        mPresenter = UpdatePresenter()
        mPresenter.mView = this
        initUI()
        initObj()
        initListener()
    }

    private fun initObj() {
        mPicDatas = ArrayList()
        picAdapter = PicAdapter(this, mPicDatas,this)
    }

    private fun initListener() {
        mLayoutBrand.setOnClickListener(this)
        mLayoutFunc.setOnClickListener(this)
        mLayoutFile.setOnClickListener(this)
        mRvPic.adapter = picAdapter
        mPresenter.getPicDataForBag(AppConfig.REQUEST_ID_GET_PIC)
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mLayoutHead = findViewById(R.id.layout_content)
        mLayoutBrand = findViewById(R.id.layout_brand)
        mLayoutFunc = findViewById(R.id.layout_func)
        mLayoutFile = findViewById(R.id.layout_file)
        mRvPic = findViewById(R.id.rv_pic)
        val gridLayoutManager = GridLayoutManager(this, 3)
        mRvPic.layoutManager = gridLayoutManager
    }


    fun showFilePopupWindow() {
        filesPopupWindow = FilePopupWindow(this,3)
        filesPopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showPhotoPopupWindow() {
        photoPopupWindow = PhotoPopupWindow(this)
        photoPopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showSizePopupWindow() {
        sizePopupWindow = NormalPopupWindow(this,4)
        sizePopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

}