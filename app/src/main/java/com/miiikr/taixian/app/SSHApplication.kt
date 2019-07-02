package com.miiikr.taixian.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.util.SparseArray
import com.miiikr.taixian.`interface`.MemoryListenerInterface
import com.squareup.leakcanary.LeakCanary
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlin.properties.Delegates
import android.app.ActivityManager
import android.util.ArrayMap
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMOptions
import com.hyphenate.easeui.EaseUI
import com.hyphenate.chat.EMMessage.ChatType
import android.content.Intent
import com.miiikr.taixian.ui.activity.ChatActivity
import com.hyphenate.chat.EMMessage
import com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo
import com.hyphenate.easeui.utils.EaseCommonUtils
import com.hyphenate.easeui.model.EaseNotifier.EaseNotificationInfoProvider




class SSHApplication : Application() {

    var api: IWXAPI? = null

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        registerComponentCallbacks(MemoryListenerInterface())
        LeakCanary.install(this)
        instance = this
        regToWx()
        val pid = android.os.Process.myPid()
        val processAppName = getAppName(pid)
        if (processAppName == null || !processAppName.equals(instance.packageName, true)) {
            return
        }
        val options =  EMOptions()
        options.acceptInvitationAlways = true// 默认添加好友时，是不需要验证的，改成需要验证
        options.autoTransferMessageAttachments = true// 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoDownloadThumbnail(true)//// 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        EMClient.getInstance().init(applicationContext, options)//初始化
        EaseUI.getInstance().init(applicationContext, options)
        EMClient.getInstance().setDebugMode(true)//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        initNotify()
    }

    fun regToWx() {
        api = WXAPIFactory.createWXAPI(this, appId, true)
        api!!.registerApp(appId)
    }

//    fun initNotify(){
//        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(object : EaseNotificationInfoProvider {
//
//            override fun getTitle(message: EMMessage): String? {
//                //修改标题,这里使用默认
//                return null
//            }
//
//            override fun getSmallIcon(message: EMMessage): Int {
//                //设置小图标，这里为默认
//                return 0
//            }
//
//            override fun getDisplayedText(message: EMMessage): String {
//                // 设置状态栏的消息提示，可以根据message的类型做相应提示
//                var ticker = EaseCommonUtils.getMessageDigest(message, instance)
//                if (message.type == EMMessage.Type.TXT) {
//                    ticker = ticker.replace("\\[.{2,3}\\]".toRegex(), "[表情]")
//                }
//                val user = getUserInfo(message.from)
//                return if (user != null) {
//                    getUserInfo(message.from)!!.nickname + ": " + ticker
//                } else {
//                    message.from + ": " + ticker
//                }
//            }
//
//            override fun getLatestText(message: EMMessage, fromUsersNum: Int, messageNum: Int): String? {
//                return null
//                // return fromUsersNum + "个基友，发来了" + messageNum + "条消息";
//            }
//
//            override fun getLaunchIntent(message: EMMessage): Intent {
//                //设置点击通知栏跳转事件
//                var intent = Intent(instance, ChatActivity::class.java)
//                //有电话时优先跳转到通话页面
////                if (isVideoCalling) {
////                    intent = Intent(appContext, VideoCallActivity::class.java)
////                } else if (isVoiceCalling) {
////                    intent = Intent(appContext, VoiceCallActivity::class.java)
////                } else {
//                    val chatType = message.chatType
//                    if (chatType == ChatType.Chat) { // 单聊信息
//                        intent.putExtra("userId", message.from)
//                        intent.putExtra("chatType", 1)
//                    } else { // 群聊信息
//                        // message.getTo()为群聊id
//                        intent.putExtra("userId", message.to)
//                        if (chatType == ChatType.GroupChat) {
//                            intent.putExtra("chatType", 2)
//                        } else {
//                            intent.putExtra("chatType", 3)
//                        }
//
//                    }
////                }
//                return intent
//            }
//        })
//    }



    companion object {
        val appId = "wx803aac2b6c966257"

        var instance: SSHApplication by Delegates.notNull()

//        var index = 0
//        var activitys: SparseArray<Activity> = SparseArray()
        var activitys:ArrayMap<String,Activity> = ArrayMap()

        fun addActivity(key:String,activity: Activity) {
            activitys[key] = activity
        }

//        fun clearAll() {
//            for (i in 0..activitys.size() - 1) {
//                val ac = activitys.valueAt(i)
//                ac.finish()
//            }
//        }
    }

    private fun getAppName(pID: Int): String? {
        var processName: String? = null
        val am = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val l = am.runningAppProcesses
        val i = l.iterator()
        val pm = this.packageManager
        while (i.hasNext()) {
            val info = i.next() as ActivityManager.RunningAppProcessInfo
            try {
                if (info.pid == pID) {
                    processName = info.processName
                    return processName
                }
            } catch (e: Exception) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }

        }
        return processName
    }

}