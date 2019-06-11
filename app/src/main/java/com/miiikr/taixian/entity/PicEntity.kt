package com.miiikr.taixian.entity

data class PicEntity(var code: Int, var picData: ArrayList<PicData>?) {
    data class PicData(var img: String?, var flag: String?, var default: Int)
}
