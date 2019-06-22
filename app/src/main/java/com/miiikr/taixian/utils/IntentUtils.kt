package com.ssh.net.ssh.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.ui.activity.*
import com.miiikr.taixian.ui.fragment.LocationActivity
import com.miiikr.taixian.ui.fragment.WriteConnInfoActivity

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

        fun toLogin(context: Context) {
            var intent = Intent()
            intent.setClass(context, LoginActivity::class.java)
            context.startActivity(intent)
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

        fun toType(context: Context,type: Int) {
            var intent = Intent()
            intent.setClass(context, ChoseTypeActivity::class.java)
            intent.putExtra("isSell",type)
            context.startActivity(intent)
        }

        fun toGoodsDetails(context: Context, from: Int,type: Int) {
            var intent = Intent()
            when (from) {
                1 -> {
                    intent.setClass(context, SellWatchActivity::class.java)
                    intent.putExtra("isSell",type)
                }
                2 -> {
                    intent.setClass(context, SellBagActivity::class.java)
                    intent.putExtra("isSell",type)
                }
                3 -> {
                    intent.setClass(context, SellJewelryActivity::class.java)
                    intent.putExtra("isSell",type)
                }
            }
            context.startActivity(intent)
        }

        fun toBrand(context: Context,request:Int,type:Int) {
            var intent = Intent()
            intent.setClass(context, BrandActivity::class.java)
            intent.putExtra("type",type)
            (context as Activity).startActivityForResult(intent,request)
        }

        fun toWallet(context: Context) {
            var intent = Intent()
            intent.setClass(context, WalletActivity::class.java)
            context.startActivity(intent)
        }

        fun toCheckDetails(context: Context,productId:String,categoryId: String){
            var intent = Intent()
            intent.setClass(context, CheckDetailsActivity::class.java)
            intent.putExtra("productId",productId)
            intent.putExtra("categoryId",categoryId)
            context.startActivity(intent)
        }

        fun toEvaDetails(context: Context,productId:String,categoryId: String){
            var intent = Intent()
            intent.setClass(context, EvaluateDetailsActivity::class.java)
            intent.putExtra("productId",productId)
            intent.putExtra("categoryId",categoryId)
            context.startActivity(intent)
        }

        fun toSellDetails(context: Context,productId:String,categoryId: String){
            var intent = Intent()
            intent.setClass(context, SellDetailsActivity::class.java)
            intent.putExtra("productId",productId)
            intent.putExtra("categoryId",categoryId)
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

        fun toPic(context: Context,sex:Int,headUrl:String){
            var intent = Intent()
            intent.putExtra("sex",sex)
            intent.putExtra("headUrl",headUrl)
            intent.setClass(context, PicActivity::class.java)
            context.startActivity(intent)
        }


        fun toBigPic(context: Context,picPath:String){
            var intent = Intent()
            intent.putExtra("path",picPath)
            intent.setClass(context, BigPicActivity::class.java)
            context.startActivity(intent)
        }

        fun toRecover(context: Context,productId: String){
            var intent = Intent()
            intent.setClass(context, RecoverActivity::class.java)
            intent.putExtra("productId",productId)
            context.startActivity(intent)
        }

        fun toLocation(context: Context,productId: String){
            var intent = Intent()
            intent.setClass(context, LocationActivity::class.java)
            intent.putExtra("productId",productId)
            context.startActivity(intent)
        }

        fun toWriteInfo(context: Context,productId: String,from: Int){
            var intent = Intent()
            intent.setClass(context, WriteConnInfoActivity::class.java)
            intent.putExtra("productId",productId)
            intent.putExtra("from",from)
            context.startActivity(intent)
        }

        fun toRecoverStore(context: Context){
            var intent = Intent()
            intent.setClass(context, StoreRecoverActivity::class.java)
            context.startActivity(intent)
        }

    }


}