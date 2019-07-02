package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.RewardPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CashEntity
import com.miiikr.taixian.ui.activity.WalletActivity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils

class BusinessFragment : BaseMvpFragment<RewardPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
          if(responseId == RequestInterface.REQUEST_CASH_DETAILS_ID){
              val result = response as? CashEntity
              if(result != null){
                  if(result.state == 1 && result.data != null){
                     mTvMoney.text = "${result.data.money}"
                      mEdtMoney.hint = "可提现金额￥${result.data.money}"
                  }else{
                      ToastUtils.toastShow(activity!!,result.message)
                  }
              }
          }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = RewardPresenter()
        mPresenter.attachView(this)
    }

    lateinit var mTvMoney:TextView
    lateinit var mEdtMoney:EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_business, null)

        contentView.findViewById<View>(R.id.iv_back).setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
        contentView.findViewById<Button>(R.id.btn_cash).setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_content, GetCashInfoFragment())
            transaction.commit()
        }
        val tv = contentView.findViewById<TextView>(R.id.iv_person)
        mEdtMoney = contentView.findViewById(R.id.edt_num)
        mTvMoney = contentView.findViewById(R.id.tv_money)
        tv.text = resources.getString(R.string.wallet_details)
        tv.setTextColor(resources.getColor(R.color.color_000000))
        tv.setOnClickListener {
            val transaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.layout_content, MoneyDetailsFragment())
            transaction.commit()
        }
//        mPresenter.getCashDetails(RequestInterface.REQUEST_CASH_DETAILS_ID,(activity as WalletActivity).sharedPreferenceUtils!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!)
        mPresenter.getCashDetails(RequestInterface.REQUEST_CASH_DETAILS_ID,"924f72b105074b43b085458489acbe27")
        return contentView
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }


}