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
import com.ssh.net.ssh.widget.FilePopupWindow
import com.ssh.net.ssh.widget.NormalPopupWindow
import com.ssh.net.ssh.widget.PhotoPopupWindow
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.lang.StringBuilder


class SellJewelryActivity : BaseMvpActivity<UpdatePresenter>(), View.OnClickListener, MainView, PicAdapter.OnPicListener {

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
            v!!.id == R.id.layout_brand -> IntentUtils.toBrand(this,CODE_BRAND_REQUEST,3)
            v!!.id == R.id.layout_material ->{
                newInstanceMaterialArrayList()
                showFuncPopupWindow(6,if (mMaterialDatas!!.size > 0) {
                    mMaterialDatas
                } else {
                    mMaterialDatas!!.addAll(mPresenter.getMaterialJewelryChose(this))
                    mMaterialDatas
                }!!)
            }
            v!!.id == R.id.layout_style ->{
                newInstanceDiamendArrayList()
                showFuncPopupWindow(5,if (mDiamandDatas!!.size > 0) {
                    mDiamandDatas
                } else {
                    mDiamandDatas!!.addAll(mPresenter.getDiamendChose(this))
                    mDiamandDatas
                }!!)
            }
            v!!.id == R.id.layout_file -> showFilePopupWindow()
        }
    }

    lateinit var mLayoutHead: LinearLayout
    lateinit var mTvTitle: TextView
    lateinit var mLayoutBrand: LinearLayout
    lateinit var mTvBrand:TextView
    lateinit var mLayoutMaterial: LinearLayout
    lateinit var mLayoutStyle: LinearLayout
    lateinit var mLayoutFile: LinearLayout
    lateinit var mRvPic: RecyclerView
    lateinit var mPicDatas: ArrayList<PicEntity.PicData>
    lateinit var picAdapter: PicAdapter
    lateinit var mTvFile:TextView
    lateinit var mTvMaterialValue:TextView
    lateinit var mTvDiamendValue:TextView

    var funcPopupWindow: NormalPopupWindow? = null
    var filesPopupWindow: FilePopupWindow? = null
    var photoPopupWindow: PhotoPopupWindow? = null
    var imageFile: File? = null
    var imageUri: Uri? = null
    val CODE_GALLERY_REQUEST = 0xa0
    val CODE_CAMERA_REQUEST = 0xa1
    val CODE_BRAND_REQUEST = 0xa2
    var fileHelper: FileHelper?=null

    var mMaterialDatas: ArrayList<ChoseEntity>? = null
    var mDiamandDatas: ArrayList<ChoseEntity>? = null
    var choseArrayMap: ArrayList<String>? = null
    var compressForTarget: HashMap<Int, File>? = null


    var brandId = ""
    var indexPic = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jewelry_details)
        mPresenter = UpdatePresenter()
        mPresenter.attachView(this)
        initUI()
        initObj()
        initListener()
    }

    private fun initObj() {
        mPicDatas = ArrayList()
        picAdapter = PicAdapter(this, mPicDatas,this)
        fileHelper = FileHelper()
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
        mTvBrand = findViewById(R.id.tv_type_value)
        mTvFile = findViewById(R.id.tv_file_value)
        mTvMaterialValue = findViewById(R.id.tv_material_value)
        mTvDiamendValue = findViewById(R.id.tv_style_value)
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.isSmoothScrollbarEnabled = true
        gridLayoutManager.isAutoMeasureEnabled = true
        mRvPic.setHasFixedSize(true)
        mRvPic.isNestedScrollingEnabled = false
        mRvPic.layoutManager = gridLayoutManager

    }

    fun showFuncPopupWindow(type:Int,datas:ArrayList<ChoseEntity>){
        funcPopupWindow = NormalPopupWindow(this,type,object :PopupClickListener{
            override fun onClick(position: Int, type: Int,flag:String) {
                if(type == 5){
                   mTvDiamendValue.text = flag
                }else if(type == 6){
                   mTvMaterialValue.text = flag
                }
            }
        },datas)
        funcPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }


    fun showFilePopupWindow() {
        newInstanceFileArrayList()
        filesPopupWindow = FilePopupWindow(this,2,object :PopupClickListener{
            override fun onClick(position: Int, type: Int, flag: String) {
                if (choseArrayMap!!.contains("$flag;")) {
                    choseArrayMap!!.remove("$flag;")
                } else {
                    choseArrayMap!!.add("$flag;")
                }
                mTvFile.text = if (choseArrayMap!!.size == 0) "" else getTextFromArray()

            }
        })
        filesPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }

    fun showPhotoPopupWindow() {
        newInstanceForImgHashMap()
        photoPopupWindow = PhotoPopupWindow(this,object :PopupClickListener{
            override fun onClick(position: Int, type: Int, flag: String) {
                photoPopupWindow!!.dismiss()
                imageFile = File(fileHelper!!.CACHE_PATH + System.currentTimeMillis() + "photo.jpg")
                when (position) {
                    1 -> {
                        imageUri = fileHelper!!.getUriForFile(this@SellJewelryActivity, imageFile)
                        PhotoUtils.takePicture(this@SellJewelryActivity, imageUri!!, CODE_CAMERA_REQUEST)
                    }
                    2 -> PhotoUtils.openPic(this@SellJewelryActivity, CODE_GALLERY_REQUEST)
                }
            }
        })
        photoPopupWindow!!.showAtLocation(R.layout.activity_jewelry_details)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_BRAND_REQUEST ->{
                    mTvBrand.text = data!!.getStringExtra("brand")
                    brandId = data!!.getStringExtra("id")
                }
                CODE_CAMERA_REQUEST -> {
                    adapterNotify()
                }
                CODE_GALLERY_REQUEST -> {
                    imageUri = data!!.data
                    var realPath = fileHelper!!.getFilePathByUri(this@SellJewelryActivity, imageUri!!, data)
                    if (realPath != null) {
                        imageFile = File(realPath)
                    }
                    adapterNotify()
                }
            }

        }else{
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


    fun newInstanceFileArrayList() {
        if (choseArrayMap == null) {
            choseArrayMap = ArrayList()
        }
    }

    fun newInstanceDiamendArrayList() {
        if (mDiamandDatas == null) {
            mDiamandDatas = ArrayList()
        }
    }

    fun newInstanceMaterialArrayList() {
        if (mMaterialDatas == null) {
            mMaterialDatas = ArrayList()
        }
    }

    fun newInstanceForImgHashMap() {
        if (compressForTarget == null) {
            compressForTarget = HashMap()
        }
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }



}