package com.ssh.net.ssh.BaseMvp.IView

interface MainView:BaseView {
    fun <T : Any> onSuccess(responseId:Int,response:T)
    fun  onFailue(responseId:Int,msg:String)
}