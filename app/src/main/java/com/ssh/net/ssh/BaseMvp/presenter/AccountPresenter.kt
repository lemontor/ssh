package com.ssh.net.ssh.BaseMvp.presenter

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.ssh.net.ssh.BaseMvp.IView.AccountView
import com.ssh.net.ssh.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AccountPresenter : BasePresenter<AccountView>() {

    fun login(requestId: Int) {

    }

    fun isAccountEmpty(account: String, pw: String): Boolean {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pw)) {
            return true
        }
        return false
    }

    fun resetPhone(context: Context, requestId: Int, phone: String) {
        Observable.create(object : ObservableOnSubscribe<SpannableString> {
            override fun subscribe(e: ObservableEmitter<SpannableString>) {
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
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView.onSuccess(requestId, it)
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
                            tvTime.text = " 60 "
                        } else {
                            tvTime.text = " ${totalSeconds}"

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


}