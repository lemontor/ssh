package com.miiikr.taixian.entity

data class CheckEntity(var state: Int, var message: String, var data: ArrayList<CheckDataEntity>?) {

    data class CheckDataEntity(var productId: String?, var categoryId: String?, var img: String?,
                               var brandName: String?, var state: Int, var gemmologistExplain: String?,var createTime:Long)

}