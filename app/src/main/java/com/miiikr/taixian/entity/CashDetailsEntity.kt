package com.miiikr.taixian.entity

data class CashDetailsEntity(var state:Int,var message:String,var data:ArrayList<CashListEntity>?) {

    data class CashListEntity(var userId:String,var ordersId:String,var ordersExplain:String,var ordersMoney:Int,var createTime:Long)
}