package com.ssh.net.ssh.widget

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.util.SparseArray
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.ssh.net.ssh.R

class EditCodeView : LinearLayout, TextWatcher, View.OnKeyListener {
    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        val editText = v as EditText
        if (keyCode == KeyEvent.KEYCODE_DEL && editText.text.length == 0) {
            val action = event!!.getAction()
            if (currentPosition != 0 && action == KeyEvent.ACTION_DOWN) {
                currentPosition--
                mEdts.get(currentPosition).requestFocus()
                mEdts.get(currentPosition).setText("")
                reSetBg(currentPosition)
            }
        }
        return false
    }

    override fun afterTextChanged(s: Editable?) {
        checkResult()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (start == 0 && count >= 1 && currentPosition != mEdts.size() - 1) {
            currentPosition++
            mEdts.get(currentPosition).isEnabled = true
            mEdts.get(currentPosition).requestFocus()
            reSetBg(currentPosition)
        }
    }

    private lateinit var mEdtOne: EditText
    private lateinit var mEdtTwo: EditText
    private lateinit var mEdtThree: EditText
    private lateinit var mEdtFour: EditText
    private lateinit var mEdts: SparseArray<EditText>
    private var currentPosition = 0

    constructor(context: Context) : super(context) {
        initUI(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUI(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initUI(context)
    }

    private fun initUI(context: Context) {
        var inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.layout_edt, this)
        mEdtOne = findViewById(R.id.edt_code_one)
        mEdtTwo = findViewById(R.id.edt_code_two)
        mEdtThree = findViewById(R.id.edt_code_three)
        mEdtFour = findViewById(R.id.edt_code_four)
        mEdts = SparseArray()
        mEdts.put(0, mEdtOne)
        mEdts.put(1, mEdtTwo)
        mEdts.put(2, mEdtThree)
        mEdts.put(3, mEdtFour)

        for (index in 0 until mEdts.size()) {
            if (index == 0) {
                mEdts.get(0).isFocusable = true
                mEdts.get(0).requestFocus()
                mEdts.get(0).isEnabled = true
            } else {
                mEdts.get(index).clearFocus()
                mEdts.get(index).isSelected = false
                mEdts.get(index).isEnabled = false
            }
            mEdts.get(index).addTextChangedListener(this)
            mEdts.get(index).setOnKeyListener(this)
        }
    }


    private fun reSetBg(curId: Int) {
        for (index in 0 until mEdts.size()) {
            if (index == curId) {
                mEdts.get(index).setBackgroundResource(R.drawable.bg_code_border_check)
            } else {
                mEdts.get(index).setBackgroundResource(R.drawable.bg_code_border_uncheck)
            }
        }
    }

    private fun checkResult(){
        if(TextUtils.isEmpty(mEdtOne.text.toString()) || TextUtils.isEmpty(mEdtTwo.text.toString())
                ||TextUtils.isEmpty(mEdtThree.text.toString())||TextUtils.isEmpty(mEdtFour.text.toString())){
            return
        }
        var sb = StringBuilder()
        sb.append(mEdtOne.text.toString())
        sb.append(mEdtTwo.text.toString())
        sb.append(mEdtThree.text.toString())
        sb.append(mEdtFour.text.toString())
        val result = sb.toString()
        if(result.length > 4){
            return
        }
        if(onCodeComplete != null){
            onCodeComplete!!.onComplete(result)
        }
    }



    private var onCodeComplete: OnCodeComplete? = null

    fun setOnCodeComplete(onCodeComplete: OnCodeComplete){
        this.onCodeComplete = onCodeComplete
    }

    interface OnCodeComplete {
        fun onComplete(code: String)
    }


}