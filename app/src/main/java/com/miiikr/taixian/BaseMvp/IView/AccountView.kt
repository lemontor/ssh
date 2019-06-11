package com.miiikr.taixian.BaseMvp.IView

interface AccountView:BaseView {
    fun <T : Any> onSuccess(responseId:Int,response:T)
    fun  onFailue(responseId:Int,msg:String)
}