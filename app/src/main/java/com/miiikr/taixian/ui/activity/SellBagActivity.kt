package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.UpdatePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.adapter.PicAdapter
import com.miiikr.taixian.entity.ChoseEntity
import com.miiikr.taixian.entity.PicEntity
import com.miiikr.taixian.entity.PicEvent
import com.miiikr.taixian.utils.FileHelper
import com.miiikr.taixian.utils.PhotoUtils
import com.miiikr.taixian.utils.RequestInterface
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.widget.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

class SellBagActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView, PicAdapter.OnPicListener {
    override fun cancel(position: Int) {
        if (compressForTarget!!.contains(position)) {
            compressForTarget!!.remove(position)
        }
    }

    override fun clickItem(position: Int) {
        if (compressForTarget == null) {
            indexPic = position
            showPhotoPopupWindow()
        } else {
            if (compressForTarget!!.contains(position)) {
                EventBus.getDefault().postSticky(PicEvent(compressForTarget!!, position))
                IntentUtils.toBigPic(this, compressForTarget!![position]!!.absolutePath)
            } else {
                indexPic = position
                showPhotoPopupWindow()
            }
        }
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
            RequestInterface.REQUEST_COMPRESS_PIC_ID -> {
                val file = response as? File
                if (file != null) {
                    compressForTarget!![indexPic] = file
                }
                compressForTarget!!.forEach {
                    Log.e("tag_map", "key:${it.key} value:${it.value}")
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_brand -> IntentUtils.toBrand(this, CODE_BRAND_REQUEST, 2)
            v!!.id == R.id.layout_func -> {
                newInstanceSizeArrayList()
                showSizePopupWindow(if (mSizeDatas!!.size > 0) {
                    mSizeDatas
                } else {
                    mSizeDatas!!.addAll(mPresenter.getSizeChose(this))
                    mSizeDatas
                }!!)
            }
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
    lateinit var mTvBrand: TextView
    lateinit var mTvSize: TextView
    lateinit var mTvFile: TextView

    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null
    var sizePopupWindow: NormalPopupWindow? = null

    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    val CODE_BRAND_REQUEST = 0xa2
    var fileHelper: FileHelper? = null

    var brandId = ""
    var indexPic = 0

    var compressForTarget: HashMap<Int, File>? = null
    var choseArrayMap: ArrayList<String>? = null
    var mSizeDatas: ArrayList<ChoseEntity>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bag_details)
        mPresenter = UpdatePresenter()
        mPresenter.attachView(this)
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
        mTvBrand = findViewById(R.id.tv_type_value)
        mTvSize = findViewById(R.id.tv_func_value)
        mTvFile = findViewById(R.id.tv_file_value)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.isSmoothScrollbarEnabled = true
        gridLayoutManager.isAutoMeasureEnabled = true
        mRvPic.setHasFixedSize(true)
        mRvPic.isNestedScrollingEnabled = false
        mRvPic.layoutManager = gridLayoutManager
    }


    fun showFilePopupWindow() {
        newInstanceFileArrayList()
        filesPopupWindow = FilePopupWindow(this, 3, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
                if (choseArrayMap!!.contains("$flag;")) {
                    choseArrayMap!!.remove("$flag;")
                } else {
                    choseArrayMap!!.add("$flag;")
                }
                mTvFile.text = if (choseArrayMap!!.size == 0) "" else getTextFromArray()

            }
        })
        filesPopupWindow!!.showAtLocation(R.layout.activity_bag_details)
    }

    fun showPhotoPopupWindow() {
        newInstanceForImgHashMap()
        photoPopupWindow = PhotoPopupWindow(this, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
                photoPopupWindow!!.dismiss()
                imageFile = File(fileHelper!!.CACHE_PATH + System.currentTimeMillis() + "photo.jpg")
                when (position) {
                    1 -> {
                        imageUri = fileHelper!!.getUriForFile(this@SellBagActivity, imageFile)
                        PhotoUtils.takePicture(this@SellBagActivity, imageUri!!, CODE_CAMERA_REQUEST)
                    }
                    2 -> PhotoUtils.openPic(this@SellBagActivity, CODE_GALLERY_REQUEST)
                }
            }
        })
        photoPopupWindow!!.showAtLocation(R.layout.activity_bag_details)
    }

    fun showSizePopupWindow(sizeData: ArrayList<ChoseEntity>) {
        sizePopupWindow = NormalPopupWindow(this, 4, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
                mTvSize.text = flag
            }
        }, sizeData)
        sizePopupWindow!!.showAtLocation(R.layout.activity_bag_details)
    }


    fun newInstanceForImgHashMap() {
        if (compressForTarget == null) {
            compressForTarget = HashMap()
        }
    }

    fun newInstanceFileArrayList() {
        if (choseArrayMap == null) {
            choseArrayMap = ArrayList()
        }
    }

    fun newInstanceSizeArrayList() {
        if (mSizeDatas == null) {
            mSizeDatas = ArrayList()
        }
    }

    fun getTextFromArray(): String {
        if (stringBuilder == null) {
            stringBuilder = StringBuilder()
        }
        stringBuilder!!.delete(0, stringBuilder!!.length)
        for (index in 0 until choseArrayMap!!.size) {
            stringBuilder!!.append(choseArrayMap!![index])
        }
        return stringBuilder.toString().substring(0, stringBuilder.toString().length - 1)
    }

    var stringBuilder: StringBuilder? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_BRAND_REQUEST -> {
                    mTvBrand.text = data!!.getStringExtra("brand")
                    brandId = data!!.getStringExtra("id")
                }
                CODE_CAMERA_REQUEST -> {
                    adapterNotify()
                }
                CODE_GALLERY_REQUEST -> {
                    imageUri = data!!.data
                    var realPath = fileHelper!!.getFilePathByUri(this@SellBagActivity, imageUri!!, data)
                    if (realPath != null) {
                        imageFile = File(realPath)
                    }
                    adapterNotify()
                }
            }
        } else {
            imageUri = null
        }
    }


    private fun adapterNotify() {
        if (imageFile != null) {
            mPresenter.compressFile(this, RequestInterface.REQUEST_COMPRESS_PIC_ID, imageFile!!)//图片压缩
            mPicDatas[indexPic].img = imageFile!!.absolutePath
            picAdapter.notifyItemChanged(indexPic)
        }
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}