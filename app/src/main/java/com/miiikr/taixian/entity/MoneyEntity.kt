package com.miiikr.taixian.entity

data class MoneyEntity(var state:Int,var message:String,var data:MoneyDetailsEntity) {
    data class MoneyDetailsEntity(var userId:String,var inviteNumber:Int,var cashBonusesSum:Double,var bonusesId:String?,var bonuses:Double,var state: Int)
}