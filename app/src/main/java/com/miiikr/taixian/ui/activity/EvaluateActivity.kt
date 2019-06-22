package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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
import com.miiikr.taixian.adapter.EvaAdapter
import com.miiikr.taixian.entity.EvaEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.SSHProgressHUD
import com.ssh.net.ssh.utils.IntentUtils
import com.yo.lg.yocheck.widget.RecycleViewDivider

class EvaluateActivity : BaseMvpActivity<PersonPresenter>(), OnClickItemListener, PersonView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_EVA_ID) {
              val resultEva =  response as? EvaEntity
              if(resultEva != null){
                  if(resultEva.state == 1){
                      if(resultEva.data != null && resultEva.data!!.size > 0){
                          mEvaData.addAll(resultEva.data!!)
                          adapter.notifyDataSetChanged()
                      }else{
                          showEmptyNotify()
                      }
                  }else{
                      showEmptyNotify()
                      ToastUtils.toastShow(this,resultEva.message)
                  }
              }else{
                  showEmptyNotify()
              }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    override fun clickItem(position: Int) {
         IntentUtils.toEvaDetails(this,mEvaData[position].productId!!,mEvaData[position].categoryId!!)
    }

    lateinit var mRvEva: RecyclerView
    lateinit var mEvaData:ArrayList<EvaEntity.EvaDataEntity>
    lateinit var adapter:EvaAdapter
    lateinit var mIvBack:ImageView
    lateinit var mFrameLayout: FrameLayout
    lateinit var viewStub: ViewStub
    lateinit var mSSHProgressHUD: SSHProgressHUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        mPresenter = PersonPresenter()
        mPresenter.attachView(this)
        initUI()
        initObj()
    }

    private fun initObj() {
        mEvaData = ArrayList()
        adapter = EvaAdapter(this,mEvaData,this)
        mRvEva.adapter = adapter
        mPresenter.getEvaData(RequestInterface.REQUEST_EVA_ID,"10086")
    }

    private fun initUI() {
        mRvEva = findViewById(R.id.rv_eva)
        mIvBack = findViewById(R.id.iv_back)
        viewStub = findViewById(R.id.contentPanel)

        mSSHProgressHUD = SSHProgressHUD.getInstance(this)
        mSSHProgressHUD.setMessage("获取数据中")
        mSSHProgressHUD.setCancelable(false)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        mRvEva.layoutManager = linearLayoutManager
        mRvEva.addItemDecoration(RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1, resources.getColor(R.color.color_F2F2F2)))
        mIvBack.setOnClickListener { finish() }
    }

    fun showEmptyNotify(){
        mFrameLayout = viewStub.inflate() as FrameLayout
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
        mPresenter.detachView()
        super.onDestroy()
    }


}