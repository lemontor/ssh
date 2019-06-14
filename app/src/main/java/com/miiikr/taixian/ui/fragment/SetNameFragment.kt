package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.AccountPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.ui.activity.MainActivity
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.miiikr.taixian.utils.ToastUtils
import com.ssh.net.ssh.utils.SpannableUtils

class SetNameFragment : BaseMvpFragment<AccountPresenter>(), AccountView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (responseId == RequestInterface.REQUEST_UPDATE_NICNNAME_ID) {
            val result = response as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    (activity as MainActivity).getSharedPreferences()!!.putValue(SharedPreferenceUtils.PREFERENCE_U_N, mEdtName.text.toString())
                    ToastUtils.toastShow(activity!!, "成功修改昵称")
                } else {
                    ToastUtils.toastShow(activity!!, result.message)
                }
            }
        } else if (RequestInterface.REQUEST_CODE_ID == responseId) {
            val result = response as? CommonEntity
            if (result != null && result.state == 1) {
                mTvCode.isEnabled = false
                mTvCode.setTextColor(activity!!.resources.getColor(R.color.color_CACACA))
                mPresenter.calculateTime(mTvCode)
                ToastUtils.toastShow(activity!!, "获取验证码成功")
            } else {
                ToastUtils.toastShow(activity!!, result!!.message)
            }
        }else if(RequestInterface.REQUEST_UPDATE_PHONE_ID == responseId){
            val result = response as? CommonEntity
            if (result != null && result.state == 1) {
                ToastUtils.toastShow(activity!!, "成功修改手机号")
            } else {
                ToastUtils.toastShow(activity!!, result!!.message)
            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
        ToastUtils.toastShow(activity!!, msg)
    }

    lateinit var mTvTitle: TextView
    lateinit var mLayoutPhone: LinearLayout
    lateinit var mTvCheck: TextView
    lateinit var mEdtName: EditText
    lateinit var mEdtPhone: EditText
    lateinit var mTvNotify: TextView
    lateinit var mTvCode: TextView
    lateinit var mIvCancel: ImageView

    var from = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = AccountPresenter()
        mPresenter.attachView(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_set_name, null)
        initUI(contentView)
        initListener()
        return contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        from = arguments!!.getInt("from")
        if (from == 0) {//更改匿名
            mLayoutPhone.visibility = View.GONE
            mTvTitle.text = resources.getString(R.string.set_account_rename)
            mEdtName.hint = resources.getString(R.string.set_account_new_nickname)
        } else {//更改手机号
            mLayoutPhone.visibility = View.VISIBLE
            mTvTitle.text = resources.getString(R.string.set_account_modify_phone)
            mEdtName.hint = resources.getString(R.string.set_account_new_phone)
            mEdtName.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }


    private fun initListener() {
        mEdtName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString())) {
                    mTvCheck.setBackgroundResource(R.drawable.bg_check_name_border_sure)
                    mTvCheck.isEnabled = true
                } else {
                    mTvCheck.setBackgroundResource(R.drawable.bg_check_name_border)
                    mTvCheck.isEnabled = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        mIvCancel.setOnClickListener {
            activity!!.supportFragmentManager.popBackStack()
        }
        mTvCheck.setOnClickListener {
            if (from == 0) {
                mPresenter.setNickName(RequestInterface.REQUEST_UPDATE_NICNNAME_ID, (activity!! as MainActivity).getSharedPreferences()!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!, mEdtName.text.toString())
            } else {
                mPresenter.setPhone(RequestInterface.REQUEST_UPDATE_PHONE_ID,(activity!! as MainActivity).getSharedPreferences()!!.getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!,mEdtName.text.toString(),mEdtPhone.text.toString())
            }
        }

        mTvCode.setOnClickListener {
            mPresenter.getCode(RequestInterface.REQUEST_CODE_ID, mEdtName.text.toString())
        }

    }

    private fun initUI(contentView: View?) {
        mTvTitle = contentView!!.findViewById(R.id.tv_title)
        mTvCheck = contentView!!.findViewById(R.id.tv_check)
        mEdtName = contentView!!.findViewById(R.id.edt_name)
        mEdtPhone = contentView!!.findViewById(R.id.edt_phone)
        mTvNotify = contentView!!.findViewById(R.id.tv_notify)
        mLayoutPhone = contentView!!.findViewById(R.id.layout_phone)
        mTvCode = contentView!!.findViewById(R.id.tv_get_code)
        mIvCancel = contentView!!.findViewById(R.id.iv_cancel)
        SpannableUtils.setTextState(activity!!, mTvNotify, mTvNotify.text.toString(), 8, 15, R.color.color_EB1616)
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

}