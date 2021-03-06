package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.TypeAdapter
import com.miiikr.taixian.entity.ProductEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.SSHProgressHUD

class ChoseTypeActivity : BaseMvpActivity<SinglePresenter>(), MainView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        val result = response as? ProductEntity
        if (result != null) {
            if (result.state == 1) {
                if (result.data != null && result.data!!.size > 0) {
                    datas!!.addAll(result.data!!)
                    adapter.notifyDataSetChanged()
                }
            } else {
                ToastUtils.toastShow(this@ChoseTypeActivity, result.message)
            }
        }

    }

    override fun onFailue(responseId: Int, msg: String) {

    }

    lateinit var mTvTitle: TextView
    lateinit var mRvType: RecyclerView
    lateinit var line: View
    private var mSSHProgressHUD: SSHProgressHUD? = null
    var datas: ArrayList<ProductEntity.DetailsEntity>? = null
    lateinit var adapter: TypeAdapter
    var isSell = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        mPresenter = SinglePresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
    }

    private fun initData() {
        isSell = intent.getIntExtra("isSell",0)
        datas = ArrayList()
        adapter = TypeAdapter(this, datas!!,isSell)
        mRvType.adapter = adapter
        mPresenter.getProduct(RequestInterface.REQUEST_TYPE_ID)
    }

    private fun initUI() {
        mTvTitle = findViewById(R.id.tv_title)
        mRvType = findViewById(R.id.rv_sub)
        mRvType.setBackgroundResource(android.R.color.white)
        line = findViewById(R.id.line)
        line.visibility = View.GONE
        mTvTitle.text = resources.getString(R.string.chose_type_title)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvType.layoutManager = layoutManager

        mSSHProgressHUD = SSHProgressHUD.getInstance(this)
        mSSHProgressHUD!!.setMessage("获取数据中")
        mSSHProgressHUD!!.setCancelable(true)

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun showLoading() {
        super.showLoading()
        mSSHProgressHUD!!.show()
    }

    override fun hideLoading() {
        super.hideLoading()
        mSSHProgressHUD!!.dismiss()
    }


}