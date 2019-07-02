package com.miiikr.taixian.entity

data class MainEntity(var states: Int, var message: String, var data: ArrayList<GoodsEntity>?) {
    data class GoodsEntity(var filePath:String, var homepageExplain: String, var homepageType: String,var releaseDetails: String, var img: String,
                           var productName: String)
}