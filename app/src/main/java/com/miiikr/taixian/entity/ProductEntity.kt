package com.miiikr.taixian.entity

data class ProductEntity(val state:Int,val message:String,var data:ArrayList<DetailsEntity>) {

    data class DetailsEntity(var categoryId:String,var categoryName:String,
                             var img:String,var filePath:String)

}