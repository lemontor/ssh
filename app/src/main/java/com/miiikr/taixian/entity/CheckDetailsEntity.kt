package com.miiikr.taixian.entity

data class CheckDetailsEntity(val state:Int,val message:String,val data:CheckDataEntity) {

    data class CheckDataEntity(var imgList:ArrayList<String>?,var gemmologistHeadPortrait:String?,var state: Int,
                               var gemmologistExplain:String?,var annexExplain:String?,var degree:String?,var watchStyle:String?,
                               var watchMaterial:String?,var productState:String?,var bagsSize:String?,
                               var diamond:String?,var jewelryMaterial:String?,var estimatedPrice:Double?)

}