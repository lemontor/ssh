package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.utils.FileHelper
import com.miiikr.taixian.utils.PhotoUtils
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.widget.PhotoPopupWindow
import java.io.File

class PicActivity :BaseMvpActivity<AccountPresenter>() {

    lateinit var mIvBack:ImageView
    lateinit var mIvMore:ImageView
    lateinit var mIvPic:ImageView

    var photoPopupWindow: PhotoPopupWindow? = null
    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    var fileHelper: FileHelper?=null
    var picWidth = 0
    var picHeight = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic)
        initUI()
        initObj()
    }

    private fun initObj() {
        fileHelper = FileHelper()
        GlideHelper.loadBitmapWithSize(this,mIvPic,R.mipmap.icon_head_man,picWidth,picHeight)
    }

    private fun initUI() {
        mIvBack = findViewById(R.id.iv_back)
        mIvMore = findViewById(R.id.iv_more)
        mIvPic = findViewById(R.id.iv_pic)

        var width = ScreenUtils.getScreenWidth(this)
        var height = (width * 1.04).toInt()
        var lp = mIvPic.layoutParams
        lp.width = width
        lp.height = height
        mIvPic.layoutParams = lp
        picWidth = width
        picHeight = height

        mIvMore.setOnClickListener {
            showPhotoPopupWindow()
        }
        mIvBack.setOnClickListener{
            finish()
        }

    }


    fun showPhotoPopupWindow() {
        photoPopupWindow = PhotoPopupWindow(this, object : OnClickItemListener {
            override fun clickItem(position: Int) {
                photoPopupWindow!!.dismiss()
                imageFile = File(fileHelper!!.CACHE_PATH + System.currentTimeMillis() + "photo.jpg")
                when (position) {
                    1 -> {
                        imageUri = fileHelper!!.getUriForFile(this@PicActivity, imageFile)
                        PhotoUtils.takePicture(this@PicActivity, imageUri!!, CODE_CAMERA_REQUEST)
                    }
                    2 -> PhotoUtils.openPic(this@PicActivity, CODE_GALLERY_REQUEST)
                }
            }
        })
        photoPopupWindow!!.showAtLocation(R.layout.activity_pic)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_CAMERA_REQUEST -> ""
                CODE_GALLERY_REQUEST -> {
                    imageUri = data!!.data
                    var realPath = fileHelper!!.getFilePathByUri(this@PicActivity, imageUri!!, data)
                    if (realPath != null) {
                        imageFile = File(realPath)
                    }
                }
            }
            if (imageFile != null) {
                GlideHelper.loadBitmapByPathWithSize(this,mIvPic,imageFile!!.absolutePath,picWidth,picHeight)
            }
        }else{
            imageUri = null
        }
    }


    override fun onDestroy() {
       var drawable =  mIvPic.drawable
       if(drawable != null){
           drawable.callback = null
       }
        mIvPic.setImageDrawable(null)
        super.onDestroy()
    }












}