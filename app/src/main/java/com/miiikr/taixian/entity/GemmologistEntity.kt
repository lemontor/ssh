package com.miiikr.taixian.entity

data class GemmologistEntity(var state:Int,val message:String,var data:ArrayList<GemmologistData>) {

    data class GemmologistData(var gemmologistId:String?,var gemmologistName:String?
                               ,var headPortrait:String?,var level:String?
                               ,var state: Int,var fullBodyPicture:String?)



}