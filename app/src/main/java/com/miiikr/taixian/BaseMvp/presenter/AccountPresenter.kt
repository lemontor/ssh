package com.miiikr.taixian.BaseMvp.presenter

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.R
import com.miiikr.taixian.app.SSHApplication
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.entity.UploadEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import com.miiikr.taixian.net.RetrofitManager2
import com.miiikr.taixian.utils.SharedPreferenceUtils
import com.sina.weibo.sdk.utils.FileUtils.getPath
import com.ssh.net.ssh.utils.GlideHelper
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.concurrent.TimeUnit

class AccountPresenter : BasePresenter<AccountView>() {

    private var mainView: AccountView? = null

    fun attachView(view: AccountView) {
        this.mainView = view
    }

    fun detachView() {
        this.mainView = null
    }

    fun isViewAttached(): Boolean {
        return mainView != null
    }


    val tag: String = "重新获取"
    val reStart: String = "获取验证码"

    fun getCode(requestId: Int, phone: String) {
        if (isPhoneEmpty(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "手机不能为空")
            return
        }
        if (isPhoneTooLen(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "请输入正确的手机格式")
            return
        }
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCode(phone).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }

                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {

                    }
                }
            })
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                        mainView!!.hideLoading()
                    }

                }
    }


    fun isPhoneEmpty(phone: String): Boolean {
        if (TextUtils.isEmpty(phone)) {
            return true
        }
        return false
    }

    fun isPhoneTooLen(phone: String): Boolean {
        if (phone.length > 11) {
            return true
        }
        return false
    }

    fun doLogin(requestId: Int, phone: String, code: String) {
        if (isPhoneEmpty(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "手机不能为空")
            return
        }
        if (isPhoneTooLen(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "请输入正确的手机格式")
            return
        }
        if (isPhoneEmpty(code) && isViewAttached()) {
            mainView!!.onFailue(requestId, "验证码不能为空")
            return
        }
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<LoginEntity> { e ->
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.doLogin(phone, code).enqueue(object : Callback<LoginEntity> {
                override fun onFailure(call: Call<LoginEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }

                }

                override fun onResponse(call: Call<LoginEntity>, response: Response<LoginEntity>) {
                    if (response?.body() != null) {
                        e.onNext(response.body()!!)
                    } else {

                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                        mainView!!.hideLoading()
                    }

                }
    }


    fun resetPhone(context: Context, requestId: Int, phone: String) {
        Observable.create(ObservableOnSubscribe<SpannableString> { e ->
            var sb = StringBuilder()
            for (index in 0 until phone.length) {
                sb.append(phone[index])
                if (index == 2 || index == 6) {
                    sb.append(" ")
                }
            }
            val result = context.resources.getString(R.string.phone_login_notify_code_phone_part_one) + sb.toString() + context.resources.getString(R.string.phone_login_notify_code_phone_part_two)
            var spannableString = SpannableString(result)
            var foregroundColorSpan = ForegroundColorSpan(context.resources.getColor(R.color.color_000000))
            spannableString.setSpan(foregroundColorSpan, 4, 17, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            e.onNext(spannableString)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)

                    }
                }
    }

    var mDisposable: Disposable? = null
    var totalSeconds = 60

    fun calculateTime(tvTime: TextView) {
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .takeUntil(Observable.timer(61, TimeUnit.SECONDS))
                .subscribe(object : Observer<Long> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        mDisposable = d
                    }

                    override fun onNext(t: Long) {
                        totalSeconds--
                        if (totalSeconds == 0) {
                            tvTime.text = reStart
                            tvTime.isEnabled = true
                            tvTime.setTextColor(tvTime.context.resources.getColor(R.color.color_EB1616))
                            totalSeconds = 60
                        } else {
                            tvTime.text = " ${totalSeconds}s $tag"
                        }
                    }

                    override fun onError(e: Throwable) {

                    }
                })
    }

    fun closeTime() {
        if (mDisposable != null && !mDisposable!!.isDisposed) {
            mDisposable!!.dispose()
        }
    }

    fun putValues(data: LoginEntity) {
        SharedPreferenceUtils(SSHApplication.instance.applicationContext)
                .putValue(SharedPreferenceUtils.PREFERENCE_U_I, data.data.userId)
                .putValue(SharedPreferenceUtils.PREFERENCE_U_P, data.data.phone)
                .putValue(SharedPreferenceUtils.PREFERENCE_U_H, data.data.headPortrait)
                .putValue(SharedPreferenceUtils.PREFERENCE_U_N, data.data.nickName)
                .putValueForInt(SharedPreferenceUtils.PREFERENCE_U_S, data.data.sex)

    }


    fun getUserInfo(requestId: Int, context: Context) {
        Observable.create(ObservableOnSubscribe<LoginEntity.UserData> {
            val share = SharedPreferenceUtils(context)
            var user = LoginEntity.UserData(share.getValue(SharedPreferenceUtils.PREFERENCE_U_P), share.getValue(SharedPreferenceUtils.PREFERENCE_U_I), "", share.getValue(SharedPreferenceUtils.PREFERENCE_U_H),
                    share.getValue(SharedPreferenceUtils.PREFERENCE_U_N), "", "", 0)
            it.onNext(user)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }


    fun compressFile(context: Context, requestId: Int, normalFile: File) {
        Luban.with(context)
                .load(normalFile)
                .ignoreBy(100)
                .setTargetDir(context.cacheDir.absolutePath)
                .setCompressListener(object : OnCompressListener {
                    override fun onSuccess(file: File?) {
                        if (file != null && file.exists()) {
                            mainView!!.onSuccess(requestId, file)
                        }
                    }

                    override fun onError(e: Throwable?) {//压缩失败时 使用原图
                        mainView!!.onSuccess(requestId, normalFile)
                    }

                    override fun onStart() {
                    }
                }).launch()
    }

    fun uploadFile(requestId: Int, file: File,userId: String) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val part = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updateHeadPic(part,userId).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    it.onNext(response.body()!!)
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }


    fun getUserInfo(requestId: Int, userId: String) {
        Observable.create(ObservableOnSubscribe<LoginEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getUserInfo(userId).enqueue(object : Callback<LoginEntity> {
                override fun onFailure(call: Call<LoginEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<LoginEntity>, response: Response<LoginEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }


    fun setNickName(requestId: Int, userId: String, nickName: String) {
        if (nickName == "") {
            if (isViewAttached()) {
                mainView!!.onFailue(requestId, "匿名不能为空")
                return
            }
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updateNickName(userId, nickName).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }

    fun setPhone(requestId: Int, userId: String, phone: String, verifyCode: String) {
        if (isPhoneEmpty(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "手机不能为空")
            return
        }
        if (isPhoneTooLen(phone) && isViewAttached()) {
            mainView!!.onFailue(requestId, "请输入正确的手机格式")
            return
        }
        if (isPhoneEmpty(verifyCode) && isViewAttached()) {
            mainView!!.onFailue(requestId, "验证码不能为空")
            return
        }
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updatePhone(userId, phone, verifyCode).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }


    fun uploadCode(requestId: Int,code: String){

        Observable.create(ObservableOnSubscribe <CommonEntity>{
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.pushCode(code).enqueue(object :Callback<CommonEntity>{
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if(response?.body() != null){
                        it.onNext(response.body()!!)
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }

    }



}