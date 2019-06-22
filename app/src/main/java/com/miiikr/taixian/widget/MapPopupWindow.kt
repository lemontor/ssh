package com.miiikr.taixian.widget

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.CardView
import android.view.View
import android.widget.TextView
import com.amap.api.maps.model.LatLng
import com.miiikr.taixian.R
import com.miiikr.taixian.`interface`.PopupClickListener
import com.miiikr.taixian.utils.ToastUtils
import com.ssh.net.ssh.widget.BasePopupWindow


class MapPopupWindow(context: Context, type: Int, onClickItemListener: PopupClickListener) : BasePopupWindow(context, type, onClickItemListener) {

    override fun initData() {
    }

    override fun initListener(context: Context) {
    }

    override fun initView(context: Context, view: View, type: Int, clickItemListener: PopupClickListener) {
        val tvGD = view.findViewById<TextView>(R.id.tv_gd)
        val tvBD = view.findViewById<TextView>(R.id.tv_bd)
        val tvTS = view.findViewById<TextView>(R.id.tv_ts)
        val layoutCancel = view.findViewById<CardView>(R.id.layout_cancel)

        layoutCancel.setOnClickListener { dismiss() }
        tvGD.setOnClickListener { toGaodeMap(context, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }
        tvBD.setOnClickListener { toBaiduMap(context, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }
        tvTS.setOnClickListener { toTencentMap(context, "深圳市南山区粤海街道科华路讯美科技广场1号楼") }

    }

    override fun getContentViews(): Int {
        return R.layout.layout_map_bottom
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