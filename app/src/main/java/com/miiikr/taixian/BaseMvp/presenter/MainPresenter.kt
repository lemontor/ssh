package com.miiikr.taixian.BaseMvp.presenter

import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.MainEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPresenter : BasePresenter<MainView>() {

    fun getMainData(requestId: Int) {
        mView.showLoading()
        Observable.create(ObservableOnSubscribe<MainEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMainData().enqueue(object : Callback<MainEntity> {
                override fun onFailure(call: Call<MainEntity>, t: Throwable) {
                    mView.onFailue(requestId, t.message!!)
                    mView.hideLoading()
                }
                override fun onResponse(call: Call<MainEntity>, response: Response<MainEntity>) {
                    if (response != null && response.body() != null) {
                        it.onNext(response.body()!!)
                    }else{

                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView.onSuccess(requestId, it)
                    mView.hideLoading()
                }
    }

    fun setSexInfo(requestId: Int, userId: String, sex: String) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.setSex(userId, sex).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    mView.onFailue(requestId, t.message!!)
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response != null && response.body() != null) {
                        it.onNext(response.body()!!)
                    }else{

                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView.onSuccess(requestId, it)
                }
    }


}