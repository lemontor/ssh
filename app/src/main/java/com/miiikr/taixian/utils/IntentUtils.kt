package com.ssh.net.ssh.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.ui.activity.*

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
            intent.putExtra("from", from)
            context.startActivity(intent)
            val ac = context as Activity
            SSHApplication.addActivity(ac)
        }


        fun toCheckCode(context: Context, phone: String) {
            var intent = Intent()
            intent.setClass(context, CheckCodeActivity::class.java)
            intent.putExtra("phone", phone)
            context.startActivity(intent)
            SSHApplication.addActivity(context as Activity)
        }

        fun toMain(context: Context) {
            var intent = Intent()
            intent.setClass(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
        }

        fun toCheck(context: Context, from: Int) {
            var intent = Intent()
            intent.setClass(context, CheckActivity::class.java)
            intent.putExtra("from", from)
            context.startActivity(intent)
        }

        fun toSub(context: Context) {
            var intent = Intent()
            intent.setClass(context, SubscribeActivity::class.java)
            context.startActivity(intent)
        }

        fun toEva(context: Context) {
            var intent = Intent()
            intent.setClass(context, EvaluateActivity::class.java)
            context.startActivity(intent)
        }

        fun toQuestion(context: Context) {
            var intent = Intent()
            intent.setClass(context, QuestionActivity::class.java)
            context.startActivity(intent)
        }

        fun toNews(context: Context) {
            var intent = Intent()
            intent.setClass(context, SystemNewsActivity::class.java)
            context.startActivity(intent)
        }

        fun toType(context: Context) {
            var intent = Intent()
            intent.setClass(context, ChoseTypeActivity::class.java)
            context.startActivity(intent)
        }

        fun toGoodsDetails(context: Context, from: Int) {
            var intent = Intent()
            if (from == 0) {
                intent.setClass(context, SellWatchActivity::class.java)
            }else if(from == 1){
                intent.setClass(context, SellBagActivity::class.java)
            }else{
                intent.setClass(context, SellJewelryActivity::class.java)
            }
            context.startActivity(intent)
        }

        fun toBrand(context: Context) {
            var intent = Intent()
            intent.setClass(context, BrandActivity::class.java)
            context.startActivity(intent)
        }

        fun toWallet(context: Context) {
            var intent = Intent()
            intent.setClass(context, WalletActivity::class.java)
            context.startActivity(intent)
        }

        fun toDetails(context: Context){
            var intent = Intent()
            intent.setClass(context, CheckDetailsActivity::class.java)
            context.startActivity(intent)
        }

        fun toAppoint(context: Context){
            var intent = Intent()
            intent.setClass(context, AppointmentActivity::class.java)
            context.startActivity(intent)
        }

        fun toMap(context: Context){
            var intent = Intent()
            intent.setClass(context, MapActivity::class.java)
            context.startActivity(intent)
        }

        fun toSex(context: Context){
            var intent = Intent()
            intent.setClass(context, SexActivity::class.java)
            context.startActivity(intent)
        }

        fun toConn(context: Context){
            var intent = Intent()
            intent.setClass(context, ConnActivity::class.java)
            context.startActivity(intent)
        }

        fun toShare(context: Context){
            var intent = Intent()
            intent.setClass(context, ShareActivity::class.java)
            context.startActivity(intent)
        }

        fun toPost(context: Context){
            var intent = Intent()
            intent.setClass(context, PostActivity::class.java)
            context.startActivity(intent)
        }

        fun toPic(context: Context){
            var intent = Intent()
            intent.setClass(context, PicActivity::class.java)
            context.startActivity(intent)
        }


    }


}