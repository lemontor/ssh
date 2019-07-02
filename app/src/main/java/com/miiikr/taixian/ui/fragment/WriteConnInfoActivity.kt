package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.widget.*
import com.miiikr.taixian.BaseMvp.IView.RecoverView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.utils.AndroidWorkaround
import com.miiikr.taixian.utils.RequestInterface
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.DatePickerPopupWindow
import com.miiikr.taixian.widget.TimePickerPopupWindow


class WriteConnInfoActivity : BaseMvpActivity<RecoverPersenter>(), RecoverView {

    override fun <T : Any> onSuccess(responseId: Int, response: T) {
        if (RequestInterface.REQUEST_RECOVER_STORY_ID == responseId) {
            val result = response as? CommonEntity
            if (result != null) {
                if (result.state == 1) {
                    ToastUtils.toastShow(this, "预约成功")

                } else {
                    ToastUtils.toastShow(this, result.message)
                }
            } else {

            }
        }
    }

    override fun onFailue(responseId: Int, msg: String) {
    }

    lateinit var mTvTimeValue: TextView
    var productId: String = ""
    var isWomen = ""
    var recoveryType = 0
    lateinit var mEdtName: EditText
    lateinit var mEdtPhone: EditText
//    lateinit var mRootLayout: RelativeLayout
    var isOpenBroad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conn_info)
        val layout = findViewById<RelativeLayout>(R.id.layout_root)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        mPresenter = RecoverPersenter()
        mPresenter.attachView(this)
        productId = intent.getStringExtra("productId")
        var from = intent.getIntExtra("from", 0)
        if (from == 2) {
            recoveryType = 2
        } else {
            recoveryType = 1
        }
        mTvTimeValue = findViewById(R.id.tv_time_value)
        mEdtName = findViewById(R.id.edt_name)
        mEdtPhone = findViewById(R.id.edt_phone)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        findViewById<TextView>(R.id.tv_time).setOnClickListener {
            initPopupWindow()
        }
        findViewById<Button>(R.id.btn_sure).setOnClickListener {
            if (mPresenter.checkInfo(this, mEdtName.text.toString(), isWomen, mEdtPhone.text.toString(), mTvTimeValue.text.toString())) {
//                mPresenter.recoverForStoryMethodOne(RequestInterface.REQUEST_RECOVER_STORY_ID, SharedPreferenceUtils(this).getValue(SharedPreferenceUtils.PREFERENCE_U_I)!!,
//                        productId, mEdtName.text.toString(), mEdtPhone.text.toString(), isWomen, mTvTimeValue.text.toString()+":00", "", "2")

                mPresenter.recoverForStoryMethodOne(RequestInterface.REQUEST_RECOVER_STORY_ID, "10086",
                        productId, mEdtName.text.toString(), mEdtPhone.text.toString(), isWomen, mTvTimeValue.text.toString() + ":00", "", "2")
            }
        }
        findViewById<RadioGroup>(R.id.rg_sex).setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_man -> isWomen = "1"
                R.id.rb_women -> isWomen = "2"
            }
        }
    }

    var datePickerPopupWindow: DatePickerPopupWindow? = null
    var timePickerPopupWindow: TimePickerPopupWindow? = null

    fun initPopupWindow() {
        if (datePickerPopupWindow == null) {
            datePickerPopupWindow = DatePickerPopupWindow(this, 0, object : PopupClickListener {
                override fun onClick(position: Int, type: Int, flag: String) {
                    mTvTimeValue.text = ""
                    datePickerPopupWindow!!.dismiss()
                    mTvTimeValue.text = flag
                    initTimePopupWindow()
                }
            })
        }
        datePickerPopupWindow!!.showAtLocation(R.layout.activity_conn_info)
    }

    fun initTimePopupWindow() {
        if (timePickerPopupWindow == null) {
            timePickerPopupWindow = TimePickerPopupWindow(this, 0, object : PopupClickListener {
                override fun onClick(position: Int, type: Int, flag: String) {
                    mTvTimeValue.append(" $flag")
                    timePickerPopupWindow!!.dismiss()
                }
            })
        }
        timePickerPopupWindow!!.showAtLocation(R.layout.activity_conn_info)
    }


    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }


}