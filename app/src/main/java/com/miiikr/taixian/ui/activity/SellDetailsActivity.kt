package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.SparseArray
import android.view.View
import android.widget.*
import com.miiikr.taixian.BaseMvp.IView.DetailsView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.DetailsPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.GoodsDetailsAdapter
import com.miiikr.taixian.entity.CheckDetailsEntity
import com.miiikr.taixian.ui.fragment.GoodsDetailsFragment
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.CustomViewPager
import com.miiikr.taixian.widget.SSHProgressHUD
import com.miiikr.taixian.widget.card.CardPageTransformer
import com.miiikr.taixian.widget.card.PageTransformerConfig
import com.ssh.net.ssh.utils.GlideHelper
import com.ssh.net.ssh.utils.IntentUtils
import com.ssh.net.ssh.utils.ScreenUtils

class SellDetailsActivity : BaseMvpActivity<DetailsPresenter>(), DetailsView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (RequestInterface.REQUEST_Sell_DETAILS_ID == responseId) {
            val result = response as? CheckDetailsEntity
            if (result != null) {
                if (result.state == 1) {
                    if (result.data != null) {
                        for (index in 0 until result.data.imgList!!.size) {
                            val url = result.data.imgList?.get(index)
                            fragments.put(index, GoodsDetailsFragment.newInstance(url!!))
                        }
                        mVpDetails.offscreenPageLimit = fragments.size() * 2
                        initPage()
                        when (categoryId) {
                            "1" -> {//手表
                                setWatchInfo(result.data)
                            }
                            "2" -> {//包包
                                mTvStyleNotify.text = "大小"
                                mLayoutFunc.visibility = View.GONE
                                mLayoutMaterial.visibility = View.GONE
                                setBagInfo(result.data)
                            }
                            "3" -> {//首饰
                                mTvStyleNotify.text = "镶钻类型"
                                mLayoutFunc.visibility = View.GONE
                                setJewelry(result.data)
                            }
                        }
                    }
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {}

    lateinit var mTvBack: TextView
    lateinit var mVpDetails: CustomViewPager
    lateinit var adapter: GoodsDetailsAdapter
    lateinit var fragments: SparseArray<Fragment>
    lateinit var mTvTalk: TextView
    lateinit var mTvSell: TextView
    lateinit var mLayoutOld: LinearLayout
    lateinit var mTvOld: TextView
    lateinit var mLayoutStyle: LinearLayout
    lateinit var mTvStyleNotify: TextView
    lateinit var mTvStyle: TextView
    lateinit var mLayoutMaterial: LinearLayout
    lateinit var mTvMaterial: TextView
    lateinit var mLayoutFunc: LinearLayout
    lateinit var mTvFunc: TextView
    lateinit var mTvFile: TextView
    lateinit var mIvHead: ImageView
    lateinit var mTvFlag: TextView


    var productId = ""
    var categoryId = ""
    var state = 0 //进行状态
    lateinit var mSSHProgressHUD: SSHProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_details)
        val layout = findViewById<RelativeLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        mPresenter = DetailsPresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
        initListener()
    }

    private fun initListener() {
        mTvBack.setOnClickListener {
            finish()
        }
        mTvSell.setOnClickListener {

            //            IntentUtils.toAppoint(CheckDetailsActivity@ this)
        }
//        mPresenter.getCheckDetailsInfo(RequestInterface.REQUEST_CHECK_DETAILS_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, productId, categoryId)
        mPresenter.getSellDetailsInfo(RequestInterface.REQUEST_Sell_DETAILS_ID, "10086", productId, categoryId)

    }

    fun setWatchInfo(data: CheckDetailsEntity.CheckDataEntity) {
        mTvOld.append(data.degree)
        if (data.watchStyle != null) {
            mTvStyle.text = data.watchStyle
        }
        if (data.watchMaterial != null) {
            mTvMaterial.text = data.watchMaterial
        }
        if (data.productState != null) {
            mTvFunc.text = data.productState
        }
        if (data.annexExplain != null) {
            mTvFile.text = data.annexExplain
        }
        GlideHelper.loadBitmpaByCircleWithPlaceholder(this, mIvHead, data.gemmologistHeadPortrait!!, R.mipmap.icon_head_man)
        stateCheck(data)

    }

    fun setBagInfo(data: CheckDetailsEntity.CheckDataEntity) {
        mTvOld.append(data.degree)
        if (data.bagsSize != null) {
            mTvStyle.text = data.bagsSize
        }
        if (data.annexExplain != null) {
            mTvFile.text = data.annexExplain
        }
        GlideHelper.loadBitmpaByCircleWithPlaceholder(this, mIvHead, data.gemmologistHeadPortrait!!, R.mipmap.icon_head_man)
        stateCheck(data)
    }

    fun setJewelry(data: CheckDetailsEntity.CheckDataEntity) {
        mTvOld.append(data.degree)
        if (data.diamond != null) {
            mTvStyle.text = data.diamond
        }
        if (data.jewelryMaterial != null) {
            mTvMaterial.text = data.jewelryMaterial
        }
        if (data.annexExplain != null) {
            mTvFile.text = data.annexExplain
        }
        GlideHelper.loadBitmpaByCircleWithPlaceholder(this, mIvHead, data.gemmologistHeadPortrait!!, R.mipmap.icon_head_man)
        stateCheck(data)
    }


    fun stateCheck(data: CheckDetailsEntity.CheckDataEntity) {
        state = data.state
        when {
            data.state == 0 -> {//0进行中
                if (data.gemmologistExplain != null) {
                    mTvFlag.text = data.gemmologistExplain
                }
                mTvSell.setBackgroundColor(this.resources.getColor(R.color.color_EAEAEA))
                mTvSell.isEnabled = false
            }
            data.state == 1 -> {//1真
//                if (data.gemmologistExplain != null) {
//                    mTvFlag.text = data.gemmologistExplain
//                }
                if(data.estimatedPrice != null){
                    mTvFlag.text = "根据图片及市场行情，目前初步报价为：${data.estimatedPrice}元，具体将辊距实物成色们决定最终价格。"
                }
            }
            data.state == 2 -> {//2假
                if (data.gemmologistExplain != null) {
                    mTvFlag.text = data.gemmologistExplain
                }
                mTvSell.setBackgroundColor(this.resources.getColor(R.color.color_EAEAEA))
                mTvSell.isEnabled = false
            }
        }
    }

    private fun initData() {
        productId = intent.getStringExtra("productId")
        categoryId = intent.getStringExtra("categoryId")
        fragments = SparseArray()


    }

    private fun initUI() {
        mTvBack = findViewById(R.id.tv_back)
        mVpDetails = findViewById(R.id.vp_goods)
        mTvTalk = findViewById(R.id.tv_talk)
        mTvSell = findViewById(R.id.tv_sell)

        mLayoutOld = findViewById(R.id.layout_old)
        mTvOld = findViewById(R.id.tv_value_constituent)

        mLayoutStyle = findViewById(R.id.layout_style)
        mTvStyleNotify = findViewById(R.id.tv_style_notify)
        mTvStyle = findViewById(R.id.tv_value_style)

        mLayoutMaterial = findViewById(R.id.layout_material)
        mTvMaterial = findViewById(R.id.tv_value_material)

        mLayoutFunc = findViewById(R.id.layout_func)
        mTvFunc = findViewById(R.id.tv_value_func)

        mTvFile = findViewById(R.id.tv_value_files)

        mIvHead = findViewById(R.id.iv_head)
        mTvFlag = findViewById(R.id.tv_check_result)

        mTvSell.visibility = View.GONE
        mSSHProgressHUD = SSHProgressHUD.getInstance(this@SellDetailsActivity)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
    }

    fun initPage() {
        mVpDetails.setPageTransformer(true, CardPageTransformer.getBuild()//建造者模式
                .addAnimationType(PageTransformerConfig.ROTATION)//默认动画 default animation rotation  旋转  当然 也可以一次性添加两个  后续会增加更多动画
                .setRotation(-45f)//旋转角度
                .addAnimationType(PageTransformerConfig.ALPHA)//默认动画 透明度 暂时还有问题
                .setViewType(PageTransformerConfig.LEFT)
                .setOnPageTransformerListener { page, position ->
                }
                .setTranslationOffset(80)
                .setScaleOffset(80)
                .create(mVpDetails))

        adapter = GoodsDetailsAdapter(fragments, supportFragmentManager)
        mVpDetails.adapter = adapter

    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun showLoading() {
        mSSHProgressHUD.show()
        super.showLoading()
    }

    override fun hideLoading() {
        mSSHProgressHUD.dismiss()
        super.hideLoading()
    }


//    fun showDialog() {
//        val builder = AlertDialog.Builder(this)
//        val dialogView = View.inflate(this, R.layout.dialog_commit, null)
//        dialogView.findViewById<Button>(R.id.btn_sure).setOnClickListener { }
//        dialogView.findViewById<TextView>(R.id.tv_back).setOnClickListener { }
//        builder.setView(dialogView)
//        val dialog = builder.create()
//        dialog.show()
//    }


}