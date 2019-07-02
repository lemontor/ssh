package com.miiikr.taixian.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hyphenate.EMMessageListener
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.EaseUI

class NewsService:Service() {

    lateinit var userName:String

    override fun onCreate() {
        super.onCreate()
        Log.e("tag_newsService","onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        userName = intent!!.getStringExtra("userName")
        val msgListener = object : EMMessageListener {
            //接收消息事件
            override fun onMessageReceived(messages: List<EMMessage>) {//收到消息
                Log.e("tag_onMessageReceived", "service_onMessageReceived")
                for (mg in messages) {

                }

            }

            override fun onCmdMessageReceived(messages: List<EMMessage>) {
                //收到透传消息
            }

            override fun onMessageRead(messages: List<EMMessage>) {
                Log.e("tag_info", "onMessageRead")
                //收到已读回执
//                if (isMessageListInited) {
//                    chatMessageList.refresh()
//                }
            }

            override fun onMessageDelivered(message: List<EMMessage>) {
                Log.e("tag_info", "onMessageDelivered")
                //收到已送达回执
//                if (isMessageListInited) {
//                    chatMessageList.refresh()
//                }
            }

            override fun onMessageRecalled(messages: List<EMMessage>) {
                //消息被撤回
            }

            override fun onMessageChanged(message: EMMessage, change: Any) {
                Log.e("tag_info", "onMessageChanged")
                //消息状态变动
//                if (isMessageListInited) {
//                    chatMessageList.refresh()
//                }
            }
        }
        EMClient.getInstance().chatManager().addMessageListener(msgListener)
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(intent: Intent?): IBinder? {
         return null
    }


}