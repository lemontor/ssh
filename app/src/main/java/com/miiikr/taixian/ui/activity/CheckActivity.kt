package com.miiikr.taixian.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import ccom.miiikr.taixian.`interface`.OnClickItemListener
import com.miiikr.taixian.BaseMvp.IView.PersonView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.PersonPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.adapter.CheckAdapter
import com.miiikr.taixian.adapter.SellAdapter
import com.miiikr.taixian.entity.CheckEntity
import com.miiikr.taixian.entity.SellEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.ProgressDialog
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.IntentUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider


class CheckActivity : BaseMvpActivity<PersonPresenter>(), OnClickItemListener, PersonView {
    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        when (responseId) {
            RequestInterface.REQUEST_CHECK_ID -> {
                val resultCheck = response as? CheckEntity
                if (resultCheck != null) {
                    if (resultCheck.state == 1) {
                        if (resultCheck.data != null && resultCheck.data!!.size > 0) {
                            mCheckData.addAll(resultCheck.data!!)
                            mAdapter.notifyDataSetChanged()
                        } else {
                            showEmptyNotify()
                        }
                    } else {
                        showEmptyNotify()
                        ToastUtils.toastShow(this, resultCheck.message)
                    }
                } else {
                    showEmptyNotify()
                }
            }
            RequestInterface.REQUEST_SELL_ID -> {
                val resultSell = response as? SellEntity
                if (resultSell != null) {
                    if (resultSell.state == 1) {
                        if (resultSell.data != null && resultSell.data!!.size > 0) {
                            mSellData.addAll(resultSell.data!!)
                            mSellAdapter.notifyDataSetChanged()
                        } else {
                            showEmptyNotify()
                        }
                    } else {
                        ToastUtils.toastShow(this, resultSell.message)
                        showEmptyNotify()
                    }
                } else {
                    showEmptyNotify()
                }
            }
        }

    }

    fun showEmptyNotify() {
        mFrameLayout = viewStub.inflate() as FrameLayout
    }


    override fun onFailue(responseId: Int, msg: String) {
        showEmptyNotify()
        if(responseId == RequestInterface.REQUEST_CHECK_ID){
           Log.e("tag_thread",Thread.currentThread().name)
        }
    }

    override fun clickItem(position: Int) {
        IntentUtils.toDetails(this)
    }

    lateinit var mRvCheck: RecyclerView
    lateinit var mAdapter: CheckAdapter
    lateinit var mSellAdapter: SellAdapter
    lateinit var mTvTitle: TextView
    lateinit var mCheckData: ArrayList<CheckEntity.CheckDataEntity>
    lateinit var mSellData: ArrayList<SellEntity.SellDataEntity>
    lateinit var mIvBack: ImageView
    lateinit var mSSHProgressHUD: SSHProgressHUD
    lateinit var mFrameLayout: FrameLayout
    lateinit var viewStub: ViewStub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)
        mPresenter = PersonPresenter()
        mPresenter.attachView(this)
        initUI()
        initObj()
        initListener()
    }

    private fun initListener() {
        mIvBack.setOnClickListener { finish() }
    }

    private fun initObj() {
        val from = intent.getIntExtra("from", 0)
        if (from == 0) {
            mTvTitle.text = resources.getString(R.string.check_title)
            mCheckData = ArrayList()
            mAdapter = CheckAdapter(this, mCheckData, this)
            mRvCheck.adapter = mAdapter
//            mPresenter.getCheckData(RequestInterface.REQUEST_CHECK_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
            mPresenter.getCheckData(RequestInterface.REQUEST_CHECK_ID, "10086")
        } else {
            mTvTitle.text = resources.getString(R.string.sell_title)
            mSellData = ArrayList()
            mSellAdapter = SellAdapter(this, mSellData, this)
            mRvCheck.adapter = mSellAdapter
//            mPresenter.getSellData(RequestInterface.REQUEST_SELL_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
            mPresenter.getSellData(RequestInterface.REQUEST_SELL_ID, "10086")
        }

    }

    private fun initUI() {
        mRvCheck = findViewById(R.id.rv_check)
        mTvTitle = findViewById(R.id.tv_title)
        mIvBack = findViewById(R.id.iv_back)
        viewStub = findViewById(R.id.contentPanel)
        mSSHProgressHUD = SSHProgressHUD.getInstance(this@CheckActivity)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(true)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvCheck.layoutManager = linearLayoutManager
        mRvCheck.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1, resources.getColor(R.color.color_F2F2F2)))

    }


    override fun showLoading() {
        mSSHProgressHUD.show()
    }

    override fun hideLoading() {
//        mSSHProgressHUD.dismiss()
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


}