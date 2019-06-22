package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.PersonView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.SubscribeAdapter
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.SubEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.SSHProgressHUD

class SubscribeActivity : BaseMvpActivity<PersonPresenter>(), PersonView,OnClickItemListener {

    override fun clickItem(position: Int) {
        cancelIndex = position
        notifyCancel()
    }

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_SUB_ID) {
            val result = response as? SubEntity
            if (result != null) {
                if (result.state == 1) {
                    if (result.data != null && result.data!!.size > 0) {
                        mSubData.addAll(result.data!!)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }else if(responseId == RequestInterface.REQUEST_CANCEL_EVA_ID){
            val result = response as? CommonEntity
            if(result != null){
                if(result.state == 1){
                   mSubData.removeAt(cancelIndex)
                    adapter.notifyDataSetChanged()
                }else{
                    ToastUtils.toastShow(this, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mRvSub: RecyclerView
    lateinit var mSSHProgressHUD: SSHProgressHUD
    lateinit var mIvBack: ImageView
    lateinit var mSubData: ArrayList<SubEntity.SubDataEntity>
    lateinit var adapter: SubscribeAdapter
    var cancelIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        mPresenter = PersonPresenter()
        mPresenter.attachView(this)
        initUI()
        initData()
    }

    private fun initData() {
        mSubData = ArrayList()
        adapter = SubscribeAdapter(this, mSubData,this)
        mRvSub.adapter = adapter

//        mPresenter.getSubData(RequestInterface.REQUEST_SUB_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
        mPresenter.getSubData(RequestInterface.REQUEST_SUB_ID, "10086")

    }

    private fun initUI() {
        mRvSub = findViewById(R.id.rv_sub)
        mIvBack = findViewById(R.id.iv_back)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvSub.layoutManager = linearLayoutManager

        mSSHProgressHUD = SSHProgressHUD.getInstance(this)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
        mIvBack.setOnClickListener { finish() }

    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    override fun hideLoading() {
        super.hideLoading()
        mSSHProgressHUD.dismiss()
    }

    override fun showLoading() {
        super.showLoading()
        mSSHProgressHUD.show()
    }

    private fun notifyCancel() {
        val builder = AlertDialog.Builder(this)
        val dialog = builder.setMessage("确认删除预约信息")
                .setPositiveButton("确定") { dialogInterface, i ->
//                    mPresenter.getCancelSellData(RequestInterface.REQUEST_CANCEL_SELL_ID, "10086", mSellData[cancelIndex].prodctId!!)
                    mPresenter.getCancelEvaData(RequestInterface.REQUEST_CANCEL_EVA_ID,"10086",mSubData[cancelIndex].productId!!,mSubData[cancelIndex].state)
                }.create()
        dialog.show()
    }

}