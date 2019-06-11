package com.miiikr.taixian.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener


class LocationService:Service(),AMapLocationListener{

    override fun onLocationChanged(p0: AMapLocation?) {

    }

    var locationClient: AMapLocationClient? = null
    var locationOption: AMapLocationClientOption? = null

    override fun onCreate() {
        super.onCreate()
        object :Thread(){
            override fun run() {
                super.run()
                initLocation()
                startLocation()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun initLocation() {
        locationClient = AMapLocationClient(this)
        locationOption = getDefaultOption()
        locationClient!!.setLocationOption(locationOption)
        locationClient!!.setLocationListener(this)
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


    override fun onDestroy() {
        super.onDestroy()
        destroyLocation()
    }



}