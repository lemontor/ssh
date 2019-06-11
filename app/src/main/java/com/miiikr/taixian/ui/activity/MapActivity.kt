package com.miiikr.taixian.ui.activity

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
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle


class MapActivity : BaseMvpActivity<MainPresenter>(), AMapLocationListener {
    override fun onLocationChanged(location: AMapLocation?) {
        if (null != location) {
            val sb = StringBuffer()
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
        mMapView = findViewById(R.id.map_view)
        if (mAMap == null) {
            mAMap = mMapView.map
        }
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

        var myLocationStyle: MyLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE)
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap!!.myLocationStyle = myLocationStyle//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap!!.isMyLocationEnabled = true
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


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }


}