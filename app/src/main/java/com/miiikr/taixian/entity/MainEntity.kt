package com.miiikr.taixian.entity

data class MainEntity(var states: Int, var message: String, var data: ArrayList<GoodsEntity>?) {
    data class GoodsEntity(var releaseDetails: String?, var img: String?, var explain: String?,
                           var productName: String?, var type: String?)
}