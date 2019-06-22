package com.miiikr.taixian.entity

data class BrandEntity(val state:Int,val message:String,var data:ArrayList<BrandDetails>?) {

    data class BrandDetails(var categoryId:String?,var brandName:String?,var brandEnglishName:String?,var brandImgLogo:String?,var brandId:String?)

}