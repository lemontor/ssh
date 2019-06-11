package com.miiikr.taixian.entity

data class BrandEntity(val state:Int,val message:String,var data:ArrayList<BrandDetails>?) {

    data class BrandDetails(var productId:String?,var productClassName:String?,var productClassEnglishName:String?,var productImgLogo:String?)

}