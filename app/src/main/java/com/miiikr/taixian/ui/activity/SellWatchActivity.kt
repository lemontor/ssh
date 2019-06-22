package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.ArrayMap
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.UpdatePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.adapter.PicAdapter
import com.miiikr.taixian.entity.*
import com.miiikr.taixian.utils.*
import com.ssh.net.ssh.utils.AppConfig
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.widget.FilePopupWindow
import com.ssh.net.ssh.widget.NormalPopupWindow
import com.ssh.net.ssh.widget.PhotoPopupWindow
import com.ssh.net.ssh.widget.TextSeekBar
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.lang.StringBuilder


class SellWatchActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView, PicAdapter.OnPicListener, TextSeekBar.OnSeekListener {
    override fun onSeek(value: String, old: String) {
        tvNewNotify.text = value
        tvNewNotify.tag = old
    }

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
            RequestInterface.REQUEST_SELL_WATCH_ID -> {
                val result = response as? CommonEntity
                if (result != null) {
                    if (result.state == 1) {
                        ToastUtils.toastShow(this, "提交成功")
                    }
                }
            }
            else -> {
                newInstanceForUpPic()
                val entity = response as? UploadEntity
                if (entity != null) {
                    if (entity.state == 1) {
                        if (entity.data != null) {
                            Log.e("tag_", "1")
                            uploadPic!![responseId] = entity.data!!
                        }
                    }
                }
                if (uploadPic!!.size == 6) {
                    if (isSell == 1) {
                        mPresenter.uploadInfoForSell(RequestInterface.REQUEST_SELL_WATCH_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, brandId, "1", tvFlag.text.toString(), tvNewNotify.tag.toString(),
                                mTvFunc.text.toString(), mTvMaterial.text.toString(), mTvStyle.text.toString(), mTvFile.text.toString(), uploadPic!!)
                    } else if (isSell == 2) {
                        mPresenter.uploadInfoForCheck(RequestInterface.REQUEST_SELL_WATCH_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, brandId, "1", tvFlag.text.toString(), tvNewNotify.tag.toString(),
                                mTvFunc.text.toString(), mTvMaterial.text.toString(), mTvStyle.text.toString(), mTvFile.text.toString(), uploadPic!!)
                    }

                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.layout_brand -> IntentUtils.toBrand(this, CODE_BRAND_REQUEST, 1)
            v!!.id == R.id.layout_func -> {
                newInstanceFuncArrayList()
                showFuncPopupWindow(1, if (mFuncDatas!!.size > 0) {
                    mFuncDatas
                } else {
                    mFuncDatas!!.addAll(mPresenter.getFuncChose(this))
                    mFuncDatas
                })
            }
            v!!.id == R.id.layout_material -> {
                newInstanceMaterialArrayList()
                showFuncPopupWindow(2, if (mMaterialDatas!!.size > 0) {
                    mMaterialDatas
                } else {
                    mMaterialDatas!!.addAll(mPresenter.getMaterialChose(this))
                    mMaterialDatas
                })
            }
            v!!.id == R.id.layout_style -> {
                newInstanceStyleArrayList()
                showFuncPopupWindow(3, if (mStyleDatas!!.size > 0) {
                    mStyleDatas
                } else {
                    mStyleDatas!!.addAll(mPresenter.getStyleChose(this))
                    mStyleDatas
                })
            }
            v!!.id == R.id.layout_file -> showFilePopupWindow()
            v!!.id == R.id.btn_upload -> {
                if (mPresenter.checkInfo(this, mSeek.getTextCount(), mTvFunc.text.toString(), mTvStyle.text.toString(), mTvMaterial.text.toString(), compressForTarget!!)) {
                    compressForTarget!!.forEach {
                        mPresenter.uploadFile(it.key, it.value)
                    }
                }
            }
        }
    }

    lateinit var mLayoutHead: LinearLayout
    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout
    lateinit var mTvBrand: TextView
    lateinit var mLayoutFunc: LinearLayout
    lateinit var mTvFunc: TextView
    lateinit var mLayoutMaterial: LinearLayout
    lateinit var mTvMaterial: TextView
    lateinit var mLayoutStyle: LinearLayout
    lateinit var mTvStyle: TextView
    lateinit var mLayoutFile: LinearLayout
    lateinit var mTvFile: TextView
    lateinit var mRvPic: RecyclerView
    lateinit var mBtnUpload: Button
    lateinit var mSeek: TextSeekBar
    lateinit var mPicDatas: ArrayList<PicEntity.PicData>
    lateinit var picAdapter: PicAdapter
    lateinit var tvNewNotify: TextView
    lateinit var tvFlag: TextView

    var chosePopupWindow: NormalPopupWindow? = null
    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null
    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    val CODE_BRAND_REQUEST = 0xa2
    var indexPic = 0
    var fileHelper: FileHelper? = null
    var brandId = "0"
    var isSell = 0 // 1 售卖  2 鉴定

    var mFuncDatas: ArrayList<ChoseEntity>? = null
    var mMaterialDatas: ArrayList<ChoseEntity>? = null
    var mStyleDatas: ArrayList<ChoseEntity>? = null
    var choseArrayMap: ArrayList<String>? = null
    var compressForTarget: HashMap<Int, File>? = null
    var uploadPic: HashMap<Int, String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_details)
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
        mLayoutMaterial.setOnClickListener(this)
        mLayoutStyle.setOnClickListener(this)
        mBtnUpload.setOnClickListener(this)
        mRvPic.adapter = picAdapter
        mPresenter.getPicDataForWatch(AppConfig.REQUEST_ID_GET_PIC)
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mLayoutHead = findViewById(R.id.layout_content)
        mLayoutBrand = findViewById(R.id.layout_brand)
        mTvBrand = findViewById(R.id.tv_type_value)
        mLayoutFunc = findViewById(R.id.layout_func)
        mLayoutMaterial = findViewById(R.id.layout_material)
        mLayoutStyle = findViewById(R.id.layout_style)
        mLayoutFile = findViewById(R.id.layout_file)
        mRvPic = findViewById(R.id.rv_pic)
        mTvFunc = findViewById(R.id.tv_func_value)
        mTvMaterial = findViewById(R.id.tv_material_value)
        mTvStyle = findViewById(R.id.tv_style_value)
        mTvFile = findViewById(R.id.tv_file_value)
        mBtnUpload = findViewById(R.id.btn_upload)
        tvNewNotify = findViewById(R.id.tv_notify)
        mSeek = findViewById(R.id.tsbar)
        tvFlag = findViewById(R.id.edt_remark)
        mSeek.setOnSeekListener(this)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.isSmoothScrollbarEnabled = true
        gridLayoutManager.isAutoMeasureEnabled = true
        mRvPic.setHasFixedSize(true)
        mRvPic.isNestedScrollingEnabled = false
        mRvPic.layoutManager = gridLayoutManager
    }


    /*
    显示功能
     */
    fun showFuncPopupWindow(itemType: Int, datas: ArrayList<ChoseEntity>?) {
        chosePopupWindow = NormalPopupWindow(this, itemType, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
                when (type) {
                    1 -> mTvFunc.text = flag
                    2 -> mTvMaterial.text = flag
                    3 -> mTvStyle.text = flag
                }
            }
        }, datas!!)
        chosePopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showFilePopupWindow() {
        newInstanceFileArrayList()
        filesPopupWindow = FilePopupWindow(this, 1, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
                if (choseArrayMap!!.contains("$flag;")) {
                    choseArrayMap!!.remove("$flag;")
                } else {
                    choseArrayMap!!.add("$flag;")
                }
                mTvFile.text = if (choseArrayMap!!.size == 0) "" else getTextFromArray()

            }
        })
        filesPopupWindow!!.showAtLocation(R.layout.activity_watch_details)
    }

    fun showPhotoPopupWindow() {
        newInstanceForImgHashMap()
        photoPopupWindow = PhotoPopupWindow(this, object : PopupClickListener {
            override fun onClick(position: Int, type: Int, flag: String) {
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
                CODE_BRAND_REQUEST -> {
                    mTvBrand.text = data!!.getStringExtra("brand")
                    brandId = data!!.getStringExtra("id")
                }
                CODE_CAMERA_REQUEST -> {
                    adapterNotify()
                }
                CODE_GALLERY_REQUEST -> {
                    imageUri = data!!.data
                    var realPath = fileHelper!!.getFilePathByUri(this@SellWatchActivity, imageUri!!, data)
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

    private fun newInstanceFuncArrayList() {
        if (mFuncDatas == null) {
            mFuncDatas = ArrayList()
        }
    }

    private fun newInstanceMaterialArrayList() {
        if (mMaterialDatas == null) {
            mMaterialDatas = ArrayList()
        }
    }

    private fun newInstanceStyleArrayList() {
        if (mStyleDatas == null) {
            mStyleDatas = ArrayList()
        }
    }

    fun newInstanceFileArrayList() {
        if (choseArrayMap == null) {
            choseArrayMap = ArrayList()
        }
    }

    fun newInstanceForImgHashMap() {
        if (compressForTarget == null) {
            compressForTarget = HashMap()
        }
    }

    fun newInstanceForUpPic() {
        if (uploadPic == null) {
            uploadPic = HashMap()
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


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}