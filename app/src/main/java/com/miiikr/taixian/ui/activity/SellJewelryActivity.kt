package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.UpdatePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.PicAdapter
import com.miiikr.taixian.entity.PicEntity
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.widget.FilePopupWindow
import com.ssh.net.ssh.widget.FuncPopupWindow
import com.ssh.net.ssh.widget.NormalPopupWindow
import com.ssh.net.ssh.widget.PhotoPopupWindow


class SellJewelryActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView, OnClickItemListener {
    override fun clickItem(position: Int) {
        showPhotoPopupWindow()
    }

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        when (responseId) {
            AppConfig.REQUEST_ID_GET_PIC -> {
                val picData = response as? PicEntity
                if (picData != null) {
                    mPicDatas.addAll(picData.picData!!)
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
            v!!.id == R.id.layout_material -> showFuncPopupWindow(6)
            v!!.id == R.id.layout_style -> showFuncPopupWindow(5)
            v!!.id == R.id.layout_file -> showFilePopupWindow()
        }
    }

    lateinit var mLayoutHead: LinearLayout
    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout
    lateinit var mLayoutMaterial: LinearLayout
    lateinit var mLayoutStyle: LinearLayout
    lateinit var mLayoutFile: LinearLayout
    lateinit var mRvPic: RecyclerView
    lateinit var mPicDatas: ArrayList<PicEntity.PicData>
    lateinit var picAdapter: PicAdapter

    var funcPopupWindow: NormalPopupWindow? = null
    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jewelry_details)
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
        mLayoutFile.setOnClickListener(this)
        mLayoutMaterial.setOnClickListener(this)
        mLayoutStyle.setOnClickListener(this)
        mRvPic.adapter = picAdapter
        mPresenter.getPicDataForWatch(AppConfig.REQUEST_ID_GET_PIC)
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mLayoutHead = findViewById(R.id.layout_content)
        mLayoutBrand = findViewById(R.id.layout_brand)
        mLayoutMaterial = findViewById(R.id.layout_material)
        mLayoutStyle = findViewById(R.id.layout_style)
        mLayoutFile = findViewById(R.id.layout_file)
        mRvPic = findViewById(R.id.rv_pic)
        val gridLayoutManager = GridLayoutManager(this, 3)
        mRvPic.layoutManager = gridLayoutManager

    }

    fun showFuncPopupWindow(type:Int){
        funcPopupWindow = NormalPopupWindow(this,type)
        funcPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }


    fun showFilePopupWindow() {
        filesPopupWindow = FilePopupWindow(this,2)
        filesPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }

    fun showPhotoPopupWindow() {
        photoPopupWindow = PhotoPopupWindow(this,object :OnClickItemListener{
            override fun clickItem(position: Int) {
            }

        })
        photoPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }


}