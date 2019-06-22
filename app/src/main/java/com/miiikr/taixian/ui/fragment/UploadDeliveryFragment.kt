package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.miiikr.taixian.BaseMvp.IView.RecoverView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.ui.activity.RecoverActivity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils

class UploadDeliveryFragment : BaseMvpFragment<RecoverPersenter>(), RecoverView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (RequestInterface.REQUEST_RECOVER_DELIVERY_ID == responseId) {
            val result = response as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    ToastUtils.toastShow(activity!!,"提交快递单号成功")
                } else {
                    ToastUtils.toastShow(activity!!, result.message)
                }
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = RecoverPersenter()
        mPresenter.attachView(this)
        val contentView = inflater.inflate(R.layout.fragment_up_delivery, null)
        val edtNum = contentView.findViewById<EditText>(R.id.edt_delivery_num)
        contentView.findViewById<ImageView>(R.id.iv_back).setOnClickListener { activity!!.supportFragmentManager.popBackStack() }
        contentView.findViewById<Button>(R.id.btn_upload).setOnClickListener {
            if(edtNum.text.toString() == ""){
               ToastUtils.toastShow(activity!!,"请填写快递单号")
            }else{
                mPresenter.recoverForStoryMethodOne(RequestInterface.REQUEST_RECOVER_DELIVERY_ID,"10086",(activity as RecoverActivity).productId,"3",edtNum.text.toString().trim())
            }
//            mPresenter.recoverForStoryMethodOne(RequestInterface.REQUEST_RECOVER_DELIVERY_ID,SharedPreferenceUtils(activity!!).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!,(activity as RecoverActivity).productId,"3",edtNum.text.toString().trim())
        }
        return contentView
    }


    override fun onDestroyView() {
        mPresenter.detachView()

        super.onDestroyView()
    }


}