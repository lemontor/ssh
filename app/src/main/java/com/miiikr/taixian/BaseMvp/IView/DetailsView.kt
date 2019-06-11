package com.miiikr.taixian.BaseMvp.IView

interface DetailsView :BaseView {
    fun <T : Any> onSuccess(responseId:Int,response:T)
    fun  onFailue(responseId:Int,msg:String)
}