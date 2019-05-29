package com.ssh.net.ssh.entity

data class PicEntity(var code: Int, var picData: ArrayList<PicData>) {
    data class PicData(val img: String, val flag: String, val default: Int)
}
