package com.miiikr.taixian.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import com.miiikr.taixian.BaseMvp.View.BaseMvpFragment
import com.miiikr.taixian.BaseMvp.presenter.SinglePresenter
import com.miiikr.taixian.R


class NetFragment : BaseMvpFragment<SinglePresenter>() {

    lateinit var layoutContent: LinearLayout
    lateinit var url: String
    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments!!.getString("url")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.fragment_net, null, false)
        contentView.findViewById<ImageView>(R.id.iv_back).setOnClickListener { activity!!.supportFragmentManager.popBackStack() }
        layoutContent = contentView.findViewById(R.id.layout_content)
        webView = WebView(activity!!)
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.javaScriptCanOpenWindowsAutomatically = true
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        webView!!.layoutParams = lp
        layoutContent.addView(webView)
        webView!!.loadUrl(url)
        webView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view!!.loadUrl(url)
                return true
            }
        }
        return contentView
    }


    override fun onDestroy() {
        if (webView != null) {
            webView!!.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView!!.clearHistory()
            layoutContent.removeView(webView)
            webView!!.destroy()
            webView = null
        }
        super.onDestroy()
    }

}