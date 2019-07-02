package com.miiikr.taixian.entity

data class WxLoginEntity(var state:Int,var message:String,var data:LoginCodeEntity) {
    data class LoginCodeEntity(var targetId:String,var mode:String)
}