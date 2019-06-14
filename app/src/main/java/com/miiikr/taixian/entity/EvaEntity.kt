package com.miiikr.taixian.entity

data class EvaEntity(var state: Int, var message: String, var data: ArrayList<EvaDataEntity>?) {

    data class EvaDataEntity(var productId:String?,var img:String?,var brandName:String?,var state:Int,
                             var price:Double,var createTime:Long)

}