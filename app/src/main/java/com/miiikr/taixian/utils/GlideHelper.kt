package com.ssh.net.ssh.utils

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.io.FileInputStream

class GlideHelper {

    companion object {

        /*
        加载圆形图片
         */
        fun loadBitmpaByCircle(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                Glide.with(activity).load(url).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
//                        val width = resource!!.intrinsicWidth
//                        val height = resource!!.intrinsicHeight
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }

        fun loadBitmpaByCircleInRes(activity: Activity?, imageView: ImageView?, res: Int) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val requestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().centerCrop()
                Glide.with(activity).load(res).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }


        fun loadBitmapWithSize(activity: Activity?, imageView: ImageView?, res: Int,width:Int,height:Int) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(1)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).override(width,height).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().centerCrop()
                Glide.with(activity).load(res).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }

        fun loadBitmapByPathWithSize(activity: Activity?, imageView: ImageView?, path: String,width:Int,height:Int) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(1)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).override(width,height).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().centerCrop()
                Glide.with(activity).load(path).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }


        /*
        加载圆角图片
         */
        fun loadBitmapByCorner(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(10)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                Glide.with(activity).load(url).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }

        /*
        加载图片
         */
        fun loadBitmapByNormal(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(1)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().centerCrop()
                Glide.with(activity).load(url).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }

        fun loadBitmapByNormal2(activity: Activity?, imageView: ImageView?, url: String) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(1)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().centerCrop().override(ScreenUtils.getScreenWidth(activity) / 2, ScreenUtils.getScreenHeight(activity) / 2)
                Glide.with(activity).load(url).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }

        fun loadInt(activity: Activity?, imageView: ImageView?, res: Int) {
            if (activity != null && !activity.isFinishing && imageView != null) {
                val cornerOptions = RoundedCorners(10)
                val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                Glide.with(activity).load(res).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
            }
        }


        fun loadIntByContext(context: Context, imageView: ImageView?, url: String) {
            val cornerOptions = RoundedCorners(10)
            val requestOptions = RequestOptions.bitmapTransform(cornerOptions).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
            Glide.with(context).load(url).apply(requestOptions).into(object :SimpleTarget<Drawable>(){
                override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                    imageView!!.setImageDrawable(resource)
                }
            })
        }


        fun compressPic(context: Context, imageView: ImageView?): String {
            val future: FutureTarget<File> = Glide.with(context).load("").downloadOnly(0, 0)
            val cacheFile = future.get()
            return cacheFile.absolutePath
        }

        var flowable: Disposable? = null

        fun compressPicByLuban(context: Context, imageView: ImageView?, url: String) {
            flowable = Flowable.create(FlowableOnSubscribe<File> {
                var target: FutureTarget<File> = Glide.with(context.applicationContext)
                        .downloadOnly()
                        .load(url)
                        .submit(ScreenUtils.getScreenWidth(context) / 2, ScreenUtils.getScreenHeight(context) / 2)
                val imageFile = target.get()
                it.onNext(imageFile)
            }, BackpressureStrategy.BUFFER)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.e("tag_file",""+it.absolutePath)
                        Luban.with(context)
                                .load(it)
                                .ignoreBy(100)
                                .setCompressListener(object : OnCompressListener {
                                    override fun onSuccess(file: File?) {
                                        Log.e("tag_file_onSuccess",""+file!!.absolutePath)
                                        if (file != null) {
                                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                                            if (bitmap != null) {
                                                imageView!!.setImageBitmap(bitmap)
                                            }
                                        }
                                    }

                                    override fun onError(e: Throwable?) {
                                        Log.e("tag_error",""+e!!.message)
                                        loadBitmapByNormal2(context as Activity, imageView, url)
                                    }

                                    override fun onStart() {
                                    }
                                })

                    }
        }

        fun cancelDisposable() {
            if (flowable != null && !flowable!!.isDisposed) {
                flowable!!.dispose()
            }
        }


    }


}