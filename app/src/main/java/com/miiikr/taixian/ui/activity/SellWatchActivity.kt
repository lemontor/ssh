package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
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
import com.miiikr.taixian.utils.FileHelper
import com.miiikr.taixian.utils.PhotoUtils
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.widget.FilePopupWindow
import com.ssh.net.ssh.widget.MarginItemDecoration
import com.ssh.net.ssh.widget.NormalPopupWindow
import com.ssh.net.ssh.widget.PhotoPopupWindow
import com.yo.lg.yocheck.widget.RecycleViewDivider
import java.io.File


class SellWatchActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView, OnClickItemListener {
    override fun clickItem(position: Int) {
        indexPic = position
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
            v!!.id == R.id.layout_func -> showFuncPopupWindow(1)
            v!!.id == R.id.layout_material -> showFuncPopupWindow(2)
            v!!.id == R.id.layout_style -> showFuncPopupWindow(3)
            v!!.id == R.id.layout_file -> showFilePopupWindow()
        }
    }

    lateinit var mLayoutHead: LinearLayout
    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout
    lateinit var mLayoutFunc: LinearLayout
    lateinit var mLayoutMaterial: LinearLayout
    lateinit var mLayoutStyle: LinearLayout
    lateinit var mLayoutFile: LinearLayout
    lateinit var mRvPic: RecyclerView
    lateinit var mPicDatas: ArrayList<PicEntity.PicData>
    lateinit var picAdapter: PicAdapter

    var chosePopupWindow: NormalPopupWindow? = null
    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null
    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    var indexPic = 0
    var fileHelper:FileHelper?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)
        mPresenter = UpdatePresenter()
        mPresenter.mView = this
        initUI()
        initObj()
        initListener()
    }

    private fun initObj() {
        mPicDatas = ArrayList()
        picAdapter = PicAdapter(this, mPicDatas, this)
        fileHelper = FileHelper()
    }

    private fun initListener() {
        mLayoutBrand.setOnClickListener(this)
        mLayoutFunc.setOnClickListener(this)
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
        mLayoutFunc = findViewById(R.id.layout_func)
        mLayoutMaterial = findViewById(R.id.layout_material)
        mLayoutStyle = findViewById(R.id.layout_style)
        mLayoutFile = findViewById(R.id.layout_file)
        mRvPic = findViewById(R.id.rv_pic)
        val gridLayoutManager = GridLayoutManager(this, 3)
        mRvPic.layoutManager = gridLayoutManager
//        mRvPic.addItemDecoration(RecycleViewDivider(
//                this, GridLayoutManager.VERTICAL, ScreenUtils.dp2px(this, 1), resources.getColor(R.color.color_ffffff)))
//        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.dp_1)
//        mRvPic.addItemDecoration(MarginItemDecoration(this@SellWatchActivity,6))
    }


    /*
    显示功能
     */
    fun showFuncPopupWindow(itemType: Int) {
        chosePopupWindow = NormalPopupWindow(this, itemType)
        chosePopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showFilePopupWindow() {
        filesPopupWindow = FilePopupWindow(this, 1)
        filesPopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showPhotoPopupWindow() {
        photoPopupWindow = PhotoPopupWindow(this, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                photoPopupWindow!!.dismiss()
                imageFile = File(fileHelper!!.CACHE_PATH + System.currentTimeMillis() + "photo.jpg")
                when (position) {
                    1 -> {
                        imageUri = fileHelper!!.getUriForFile(this@SellWatchActivity, imageFile)
                        PhotoUtils.takePicture(this@SellWatchActivity, imageUri!!, CODE_CAMERA_REQUEST)
                    }
                    2 -> PhotoUtils.openPic(this@SellWatchActivity, CODE_GALLERY_REQUEST)
                }
            }
        })
        photoPopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_CAMERA_REQUEST -> ""
                CODE_GALLERY_REQUEST -> {
                    imageUri = data!!.data
                    var realPath = fileHelper!!.getFilePathByUri(this@SellWatchActivity, imageUri!!, data)
                    if (realPath != null) {
                        imageFile = File(realPath)
                    }
                }
            }
            if (imageFile != null) {
                mPicDatas[indexPic].img = imageFile!!.absolutePath
                picAdapter.notifyItemChanged(indexPic)
            }
        }else{
            imageUri = null
        }
    }


}