package com.miiikr.taixian.entity

data class CashEntity(var state:Int,var message:String,var data:CashDetailsEntity) {
    data class CashDetailsEntity(var userId:String,var money:Double)
}