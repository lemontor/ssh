package com.miiikr.taixian.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.miiikr.taixian.R
import com.ssh.net.ssh.utils.SpannableUtils

class QuestionActivity : AppCompatActivity() {
    lateinit var mTvDetailsA: TextView
    lateinit var mTvDetailsB: TextView
    lateinit var mTvDetailsC: TextView
    lateinit var mTvDetailsD: TextView
    lateinit var mTvDetailsE: TextView
    lateinit var mTvDetailsF: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        initUI()
    }

    private fun initUI() {
        mTvDetailsA = findViewById(R.id.tv_a)
        mTvDetailsB = findViewById(R.id.tv_b)
        mTvDetailsC = findViewById(R.id.tv_c)
        mTvDetailsD = findViewById(R.id.tv_d)
        mTvDetailsE = findViewById(R.id.tv_e)
        mTvDetailsF = findViewById(R.id.tv_f)
        SpannableUtils.setMargin(this, mTvDetailsA)
        SpannableUtils.setMargin(this, mTvDetailsB)
        SpannableUtils.setMargin(this, mTvDetailsC)
        SpannableUtils.setMargin(this, mTvDetailsD)
        SpannableUtils.setMargin(this, mTvDetailsE)
        SpannableUtils.setMargin(this, mTvDetailsF)
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
    }

}