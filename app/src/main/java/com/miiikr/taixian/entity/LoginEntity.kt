package com.miiikr.taixian.entity

data class LoginEntity(var erroCode: Int, var data: UserData) {
    data class UserData(var phone: String?, var userId: String?, var password: String?, var headPortrait: String?,
                        var nickName: String?, var version: String?, var createTime: String?, var set: Int)
}
