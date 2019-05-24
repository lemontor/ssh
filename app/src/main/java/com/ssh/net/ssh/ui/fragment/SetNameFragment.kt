package com.ssh.net.ssh.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.View.BaseMvpFragment
import com.ssh.net.ssh.BaseMvp.presenter.AccountPresenter
import com.ssh.net.ssh.R
import com.ssh.net.ssh.utils.SpannableUtils

class SetNameFragment : BaseMvpFragment<AccountPresenter>() {

    lateinit var mTvTitle: TextView
    lateinit var mLayoutPhone: LinearLayout
    lateinit var mTvCheck: TextView
    lateinit var mEdtName: EditText
    lateinit var mEdtPhone: EditText
    lateinit var mTvNotify: TextView
    lateinit var mTvCode: TextView
    lateinit var mIvCancel:ImageView

    var from = 0

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
        }
    }


    private fun initListener() {
        mEdtName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString()) && s.toString().length > 6) {
                    mTvCheck.setBackgroundResource(R.drawable.bg_check_name_border_sure)
                } else {
                    mTvCheck.setBackgroundResource(R.drawable.bg_check_name_border)
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

}