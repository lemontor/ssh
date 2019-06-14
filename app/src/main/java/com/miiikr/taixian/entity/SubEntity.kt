package com.miiikr.taixian.entity



data class SubEntity(var state: Int, var message: String, var data: ArrayList<SubDataEntity>?) {
    data class SubDataEntity(var recoveryTypeId: Int, var reservationId: Int, var productId: String?,
                             var gemmologistInfo: LogistEntity?, var createTime: Long, var reservationTime: Long,
                             var state: Int, var recoveryInfoId: Int, var expressId: String?) {
        data class LogistEntity(var gemmologistId: String?, var gemmologistName: String?, var headPortrait: String?, var level: String?)
    }

}