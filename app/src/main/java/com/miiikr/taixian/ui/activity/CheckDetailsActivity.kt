package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.SparseArray
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.DetailsPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.GoodsDetailsAdapter
import com.miiikr.taixian.ui.fragment.GoodsDetailsFragment
import com.miiikr.taixian.widget.CustomViewPager
import com.miiikr.taixian.widget.card.CardPageTransformer
import com.miiikr.taixian.widget.card.PageTransformerConfig
import com.ssh.net.ssh.utils.IntentUtils

class CheckDetailsActivity:BaseMvpActivity<DetailsPresenter>() {

    lateinit var mTvBack:TextView
    lateinit var mVpDetails:CustomViewPager
    lateinit var adapter:GoodsDetailsAdapter
    lateinit var fragments:SparseArray<Fragment>
    lateinit var mTvTalk:TextView
    lateinit var mTvSell:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_details)
        initUI()
        initData()
        initPage()
        initListener()
    }

    private fun initListener() {
        mTvSell.setOnClickListener { IntentUtils.toAppoint(CheckDetailsActivity@this) }
    }

    private fun initData() {
        fragments = SparseArray()
        for (index in 0 until 4){
            fragments.put(index,GoodsDetailsFragment.newInstance(""))
        }
        mVpDetails.offscreenPageLimit =  fragments.size() * 2
    }

    private fun initUI() {
         mTvBack = findViewById(R.id.tv_back)
         mVpDetails = findViewById(R.id.vp_goods)
        mTvTalk = findViewById(R.id.tv_talk)
        mTvSell = findViewById(R.id.tv_sell)

    }

    fun initPage(){
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
        adapter = GoodsDetailsAdapter(fragments,supportFragmentManager)
        mVpDetails.adapter = adapter

    }



}