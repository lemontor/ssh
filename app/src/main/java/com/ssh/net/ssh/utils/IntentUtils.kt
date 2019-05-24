package com.ssh.net.ssh.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ssh.net.ssh.app.SSHApplication
import com.ssh.net.ssh.ui.activity.*

class IntentUtils {

    companion object {
        /*
        参数二：表示从哪个页面跳转过来的
        1：点击快捷登录
        2：点击忘记密码
        3: 更改密码
         */
        fun toPhoneLogin(context: Context, from: Int) {
            var intent = Intent()
            intent.setClass(context, PhoneLoginActivity::class.java)
            intent.putExtra("from",from)
            context.startActivity(intent)
            val ac = context as Activity
            SSHApplication.addActivity(ac)
        }


        fun toCheckCode(context: Context,phone:String){
            var intent = Intent()
            intent.setClass(context,CheckCodeActivity::class.java)
            intent.putExtra("phone",phone)
            context.startActivity(intent)
            SSHApplication.addActivity(context as Activity)
        }

        fun toMain(context: Context){
            var intent = Intent()
            intent.setClass(context,MainActivity::class.java)
            context.startActivity(intent)
        }

        fun toCheck(context: Context,from: Int){
            var intent = Intent()
            intent.setClass(context,CheckActivity::class.java)
            intent.putExtra("from",from)
            context.startActivity(intent)
        }

        fun toSub(context: Context){
            var intent = Intent()
            intent.setClass(context,SubscribeActivity::class.java)
            context.startActivity(intent)
        }

        fun toEva(context: Context){
            var intent = Intent()
            intent.setClass(context,EvaluateActivity::class.java)
            context.startActivity(intent)
        }

        fun toQuestion(context: Context){
            var intent = Intent()
            intent.setClass(context,QuestionActivity::class.java)
            context.startActivity(intent)
        }

        fun toNews(context: Context){
            var intent = Intent()
            intent.setClass(context,SystemNewsActivity::class.java)
            context.startActivity(intent)
        }

        fun toType(context: Context){
            var intent = Intent()
            intent.setClass(context,ChoseTypeActivity::class.java)
            context.startActivity(intent)
        }

        fun toThing(context: Context){
            var intent = Intent()
            intent.setClass(context,ThingActivity::class.java)
            context.startActivity(intent)
        }

        fun toBrand(context: Context){
            var intent = Intent()
            intent.setClass(context,BrandActivity::class.java)
            context.startActivity(intent)
        }

    }


}