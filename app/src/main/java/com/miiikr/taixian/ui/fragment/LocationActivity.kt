package com.miiikr.taixian.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.*
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.RecoverPersenter
import com.miiikr.taixian.R
import com.miiikr.taixian.utils.AndroidWorkaround
import com.ssh.net.ssh.utils.IntentUtils

class LocationActivity : BaseMvpActivity<RecoverPersenter>() {
    var jingdu = 113.947301
    var weidu = 22.54621

    lateinit var mMapView: MapView
    lateinit var mIvBack: ImageView
    var productId:String = ""

    var mAMap: AMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_location)
        val layout = findViewById<RelativeLayout>(R.id.root_layout)
        layout.setPadding(0, 0, 0, AndroidWorkaround.getNavigationBarHeight(this))
        initUI()
        mMapView.onCreate(savedInstanceState)
    }

    private fun initUI() {
        productId = intent.getStringExtra("productId")
        findViewById<ImageView>(R.id.iv_back).setOnClickListener { finish() }
        findViewById<Button>(R.id.btn_next).setOnClickListener { IntentUtils.toWriteInfo(this,productId,1) }
        findViewById<TextView>(R.id.tv_phone).setOnClickListener { }

        mMapView = findViewById(R.id.map_view)
        if (mAMap == null) {
            mAMap = mMapView.map
        }
        //绘制marker
        val latLng = LatLng(weidu, jingdu)
        val marker = mAMap!!.addMarker(MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(resources, R.mipmap.icon_location)))
                .draggable(true))
        mAMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
    }


    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }


    fun showLocationFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.addToBackStack(null)
        transaction.replace(R.id.layout_content, fragment)
        transaction.commit()
    }


}