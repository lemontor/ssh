package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.utils.*
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.ScreenUtils
import com.ssh.net.ssh.widget.PhotoPopupWindow
import java.io.File

class PicActivity : BaseMvpActivity<AccountPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_COMPRESS_PIC_ID) {
            val file = response as? File
            if (file != null) {
                mPresenter.uploadFile(RequestInterface.REQUEST_UP_PIC_ID, file, getSharedPreference()!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
                GlideHelper.loadBitmapByPathWithSize(this, mIvPic, file!!.absolutePath, picWidth, picHeight)
            }
        } else if (responseId == RequestInterface.REQUEST_UP_PIC_ID) {
            val result = response as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    ToastUtils.toastShow(this,"上传头像成功")
                    getSharedPreference()!!.putValue(SharedPreferenceUtils.PREFERENCE_U_H, result.data)
                } else {
                    ToastUtils.toastShow(this, "修改头像失败")
                }
            } else {
                ToastUtils.toastShow(this, "修改头像失败")
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mIvBack: ImageView
    lateinit var mIvMore: ImageView
    lateinit var mIvPic: ImageView

    var photoPopupWindow: PhotoPopupWindow? = null
    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    var fileHelper: FileHelper? = null
    var picWidth = 0
    var picHeight = 0
    var sex = 0
    var headUrl = ""
    var share: SharedPreferenceUtils? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic)
        sex = intent.getIntExtra("sex", 0)
        headUrl = intent.getStringExtra("headUrl")
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
        initUI()
        initObj()
    }

    private fun initObj() {
        fileHelper = FileHelper()
        if (headUrl != null && headUrl != "") {
            GlideHelper.loadBitmpaByCircleWithSize(this, mIvPic, headUrl, picWidth, picHeight)
        } else {
            if (sex == 1) {
                GlideHelper.loadBitmapWithSize(this, mIvPic, R.mipmap.icon_head_man, picWidth, picHeight)
            } else {
                GlideHelper.loadBitmapWithSize(this, mIvPic, R.mipmap.icon_head_women, picWidth, picHeight)
            }
        }
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
        mIvBack.setOnClickListener {
            finish()
        }

    }


    fun showPhotoPopupWindow() {
        photoPopupWindow = PhotoPopupWindow(this, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
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
                mPresenter.compressFile(this@PicActivity, RequestInterface.REQUEST_COMPRESS_PIC_ID, imageFile!!)
            }
        } else {
            imageUri = null
        }
    }


    override fun onDestroy() {
        var drawable = mIvPic.drawable
        if (drawable != null) {
            drawable.callback = null
        }
        mIvPic.setImageDrawable(null)
        mPresenter.detachView()
        super.onDestroy()
    }


    fun getSharedPreference():SharedPreferenceUtils?{
        if(share == null){
            share = SharedPreferenceUtils(this)
        }
        return share
    }


}