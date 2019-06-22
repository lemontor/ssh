package com.miiikr.taixian.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.miiikr.taixian.BaseMvp.View.BaseMvpActivity
import com.miiikr.taixian.BaseMvp.presenter.MainPresenter
import com.miiikr.taixian.R
import com.miiikr.taixian.utils.Test
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.support.v7.widget.CardView
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.utils.ToastUtils
import com.miiikr.taixian.widget.MapPopupWindow


class MapActivity : BaseMvpActivity<MainPresenter>(), AMapLocationListener {
    override fun onLocationChanged(location: AMapLocation?) {
        if (null != location) {
            val sb = StringBuffer()
            Log.e("tag_onLocationChanged", "onLocationChanged")
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {

                //绘制marker
                val marker = mAMap!!.addMarker(MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(resources, R.mipmap.icon_map_position)))
                        .draggable(true))


                sb.append("定位成功" + "\n")
                sb.append("定位类型: " + location.getLocationType() + "\n")
                sb.append("经    度    : " + location.getLongitude() + "\n")
                sb.append("纬    度    : " + location.getLatitude() + "\n")
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n")
                sb.append("提供者    : " + location.getProvider() + "\n")

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n")
                sb.append("角    度    : " + location.getBearing() + "\n")
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n")
                sb.append("国    家    : " + location.getCountry() + "\n")
                sb.append("省            : " + location.getProvince() + "\n")
                sb.append("市            : " + location.getCity() + "\n")
                sb.append("城市编码 : " + location.getCityCode() + "\n")
                sb.append("区            : " + location.getDistrict() + "\n")
                sb.append("区域 码   : " + location.getAdCode() + "\n")
                sb.append("地    址    : " + location.getAddress() + "\n")
                sb.append("兴趣点    : " + location.getPoiName() + "\n")
                //定位完成的时间
//                sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n")
            } else {
                //定位失败
                sb.append("定位失败" + "\n")
                sb.append("错误码:" + location.getErrorCode() + "\n")
                sb.append("错误信息:" + location.getErrorInfo() + "\n")
                sb.append("错误描述:" + location.getLocationDetail() + "\n")
            }
            sb.append("***定位质量报告***").append("\n")
            sb.append("* WIFI开关：").append(if (location.getLocationQualityReport().isWifiAble()) "开启" else "关闭").append("\n")
//            sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n")
            sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n")
            sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n")
            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n")
            sb.append("****************").append("\n")
            //定位之后的回调时间
//            sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n")

            //解析定位结果，
            val result = sb.toString()
            Log.e("tag_map", result)
//            tvResult.setText(result)
        } else {
            Log.e("tag_map", "定位失败，loc is null")

        }
    }

    lateinit var mMapView: MapView
    var mAMap: AMap? = null
    var locationClient: AMapLocationClient? = null
    var locationOption: AMapLocationClientOption? = null
    var jingdu = 113.947301
    var weidu = 22.54621
    lateinit var rootView: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initUI()
        mMapView.onCreate(savedInstanceState)
        initLocation()
        startLocation()
        Log.e("tag_sha1", Test.sHA1(this@MapActivity))

    }

    private fun initLocation() {
        locationClient = AMapLocationClient(this)
        locationOption = getDefaultOption()
        locationClient!!.setLocationOption(locationOption)
        locationClient!!.setLocationListener(this)
    }

    private fun initUI() {
        rootView = findViewById(R.id.layout_root)
        findViewById<Button>(R.id.tv_next).setOnClickListener {
            newInstancePopuWindow()
        }
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

    fun getDefaultOption(): AMapLocationClientOption {
        var option = AMapLocationClientOption()
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        option.isGpsFirst = false
        option.httpTimeOut = 30000
        option.interval = 2000
        option.isNeedAddress = true
        option.isOnceLocation = false
        option.isOnceLocationLatest = false
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)
        option.isSensorEnable = false
        option.isWifiScan = true
        option.isLocationCacheEnable = true
        option.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT
        return option
    }


    fun startLocation() {
        locationClient!!.setLocationOption(locationOption)
        locationClient!!.startLocation()
    }

    fun stopLocation() {
        locationClient!!.stopLocation()
    }

    fun destroyLocation() {
        if (null != locationClient) {
            locationClient!!.onDestroy()
            locationClient = null
            locationOption = null
        }
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
        stopLocation()
        destroyLocation()
        mMapView.onDestroy()
    }

    var mapWindow: PopupWindow? = null

    fun newInstancePopuWindow() {
        if (mapWindow == null) {
            val contentView = layoutInflater.inflate(R.layout.layout_map_bottom, null, false)
            mapWindow = PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
            val tvGD = contentView.findViewById<TextView>(R.id.tv_gd)
            val tvBD = contentView.findViewById<TextView>(R.id.tv_bd)
            val tvTS = contentView.findViewById<TextView>(R.id.tv_ts)
            val layout = contentView.findViewById<RelativeLayout>(R.id.root_layout)
            val layoutCancel = contentView.findViewById<CardView>(R.id.layout_cancel)
            layout.setOnClickListener { mapWindow!!.dismiss() }
            layoutCancel.setOnClickListener { mapWindow!!.dismiss() }
            tvGD.setOnClickListener { toGaodeMap(this, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }
            tvBD.setOnClickListener { toBaiduMap(this, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }
            tvTS.setOnClickListener { toTencentMap(this, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }
            mapWindow!!.isFocusable = true//设置获取焦点
            mapWindow!!.isTouchable = true//设置可以触摸
            mapWindow!!.isOutsideTouchable = true//设置外边可以点击
            mapWindow!!.setBackgroundDrawable(ColorDrawable(0xffffff))
            // 设置背景颜色变暗
            val dw = ColorDrawable(0xffffff)
            mapWindow!!.setBackgroundDrawable(dw)
            mapWindow!!.animationStyle = R.style.BottomDialogWindowAnim
        }
        mapWindow!!.showAtLocation(LayoutInflater.from(this).inflate(R.layout.activity_map, null),
                Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }


    fun isInstalled(context: Context, packageName: String): Boolean {
        val manager = context.packageManager
        val installedPackages = manager.getInstalledPackages(0)
        if (installedPackages != null) {
            for (index in 0 until installedPackages.size) {
                if (installedPackages[index].packageName == packageName) {
                    return true
                }
            }
        }
        return false
    }

    fun toBaiduMap(context: Context, mAddressStr: String) {
        if (!isInstalled(context, "com.baidu.BaiduMap")) {
            ToastUtils.toastShow(context, "请先安装百度地图客户端")
            return
        }
        val intent = Intent()
        intent.data = Uri.parse("baidumap://map/direction?destination=latlng:"
                + 22.54621 + ","
                + 113.947301 + "|name:" + mAddressStr +
                "&mode=driving" +
                "&src=" + context.packageName)
        context.startActivity(intent)
    }

    fun toGaodeMap(context: Context, mAddressStr: String) {
        if (!isInstalled(context, "com.autonavi.minimap")) {
            ToastUtils.toastShow(context, "请先安装高德地图客户端")
            return
        }
        var endPoint = BD2GCJ(LatLng(22.54621, 113.947301))
        var stringBuffer = StringBuffer("androidamap://navi?sourceApplication=").append("amap")
        stringBuffer.append("&lat=").append(endPoint.latitude)
                .append("&lon=").append(endPoint.longitude
                ).append("&keywords=$mAddressStr")
                .append("&dev=").append(0).append("&style=").append(2)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()))
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent)
    }

    fun toTencentMap(context: Context, mAddressStr: String) {
        if (!isInstalled(context, "com.tencent.map")) {
            ToastUtils.toastShow(context, "请先安装腾讯地图客户端")
            return
        }
        val endPoint = BD2GCJ(LatLng(22.54621, 113.947301))
        val stringBuffer = StringBuffer("qqmap://map/routeplan?type=drive")
                .append("&tocoord=").append(endPoint.latitude).append(",").append(endPoint.longitude).append("&to=" + mAddressStr)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(stringBuffer.toString()))
        context.startActivity(intent)
    }


    fun BD2GCJ(bd: LatLng): LatLng {
        var x: Double = bd.longitude - 0.0065
        var y: Double = bd.latitude - 0.006
        val z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI)
        val theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI)
        val lng = z * Math.cos(theta)//lng
        val lat = z * Math.sin(theta)//lat
        return LatLng(lat, lng)
    }


}