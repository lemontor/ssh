package com.miiikr.taixian.entity

data class ProductEntity(val state:Int,val message:String,var data:ArrayList<DetailsEntity>?) {

    data class DetailsEntity(var productId:String?,var productName:String?,var productBrand:String?,
                             var img:String?)

}