package com.miiikr.taixian.entity

data class MoneyCompleteEntity(var state:Int,var message:String,var data:MoneyCompleteDetailsEntity) {

    data class MoneyCompleteDetailsEntity(var bonusesId:String,var money:Double)

}