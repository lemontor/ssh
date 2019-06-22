package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.UpdatePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.BrandAdapter
import com.miiikr.taixian.entity.BrandEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.SpannableUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class BrandActivity : BaseMvpActivity<UpdatePresenter>(), MainView, OnClickItemListener {
    override fun clickItem(position: Int) {
        val brand = mData[position]
        var intent = Intent()
        intent.putExtra("id",brand.brandId)
        intent.putExtra("brand",brand.brandName+" "+brand.brandEnglishName)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        val result = response as? BrandEntity
        if (result != null) {
            if (result.data != null && result.data!!.size > 0) {
                mData.addAll(result.data!!)
                adapter.notifyDataSetChanged()
            } else {
                ToastUtils.toastShow(this@BrandActivity, result.message)
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mRvBrand: RecyclerView
    lateinit var mData: ArrayList<BrandEntity.BrandDetails>
    lateinit var adapter: BrandAdapter
    lateinit var mSSHProgressHUD: SSHProgressHUD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand)
        mPresenter = UpdatePresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
    }

    private fun initData() {
        mData = ArrayList()
        adapter = BrandAdapter(this, mData, this)
        mRvBrand.adapter = adapter
        mPresenter.getBrand(RequestInterface.REQUEST_WATCH_BEAND_ID, "1")
    }

    private fun initUI() {
        mRvBrand = findViewById(R.id.rv_brand)
        mSSHProgressHUD = SSHProgressHUD.getInstance(this@BrandActivity)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvBrand.layoutManager = linearLayoutManager
        mRvBrand.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL))
    }


    override fun showLoading() {
        super.showLoading()
        mSSHProgressHUD.show()
    }

    override fun hideLoading() {
        super.hideLoading()
        mSSHProgressHUD.dismiss()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}