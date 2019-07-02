package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils

class AdviceActivity :BaseMvpActivity<SinglePresenter>(), MainView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        val result = response as? CommonEntity
        if(result != null){
            if(result.state == 1){
                ToastUtils.toastShow(this,"谢谢您的意见，您的反馈我们会认真对待。")
                finish()
            }else{
                ToastUtils.toastShow(this,result.message)
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)
        mPresenter = SinglePresenter()
        mPresenter.attachView(this)
        initUI()
    }

    private fun initUI() {
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        val editText = findViewById<EditText>(R.id.edt_advice)
        findViewById<Button>(R.id.btn_up).setOnClickListener {
            if(editText.text.toString() == ""){
                ToastUtils.toastShow(this@AdviceActivity,"请输入您的问题")
            }else{
                mPresenter.advice(RequestInterface.REQUEST_ADVICE_ID,"10086",editText.text.toString().trim())
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}