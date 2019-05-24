package com.ssh.net.ssh.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class GlideHelper {

    companion object {

        /*
        加载圆形图片
         */
        fun loadBitmpaByCircle(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(activity).load(url).apply(requestOptions).into(imageView)
            }
        }

        /*
        加载圆角图片
         */
        fun loadBitmapByCorner(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(10)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(activity).load(url).apply(requestOptions).into(imageView)
            }
        }

        /*
        加载图片
         */
        fun loadBitmapByNormal(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(1)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(activity).load(url).apply(requestOptions).into(imageView)
            }
        }

        fun loadInt(activity: Activity?, imageView: ImageView?, res: Int) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(10)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(activity).load(res).apply(requestOptions).into(imageView)
            }
        }


        fun loadIntByContext(context: Context, imageView: ImageView?, url:String) {
                val cornerOptions = RoundedCorners(10)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL)
                Glide.with(context).load(url).apply(requestOptions).into(imageView)
        }

    }


}