package com.miiikr.taixian.entity

data class MoneyStateEntity(var state:Int,var message:String,var data:ArrayList<MoneyStateDetailsEntity>?) {

    data class MoneyStateDetailsEntity(var userId:String,var bonusesId:String,var bonuses:Int,var state: Int,var bonusesType:Int?)
}