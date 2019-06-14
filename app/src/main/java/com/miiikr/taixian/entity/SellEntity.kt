package com.miiikr.taixian.entity

data class SellEntity(var state:Int,var message:String,var data:ArrayList<SellDataEntity>?) {

    data class SellDataEntity(var productId:String?,var state:Int,var img:String?,
                              var brandName:String?,var recoveryType:Int,var createTime:Long)
}