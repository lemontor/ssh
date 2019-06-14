package com.miiikr.taixian.BaseMvp.presenter

import android.util.Log
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.IView.PersonView
import com.miiikr.taixian.entity.CheckEntity
import com.miiikr.taixian.entity.EvaEntity
import com.miiikr.taixian.entity.SellEntity
import com.miiikr.taixian.entity.SubEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import com.miiikr.taixian.net.RetrofitManager2
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PersonPresenter : BasePresenter<PersonView>() {


    private var mainView: PersonView? = null

    fun attachView(view: PersonView) {
        this.mainView = view
    }

    fun detachView() {
        this.mainView = null
    }

    fun isViewAttached(): Boolean {
        return mainView != null
    }

    fun getSubData(requestId: Int, userId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<SubEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getSubData(userId).enqueue(object : Callback<SubEntity> {
                override fun onFailure(call: Call<SubEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<SubEntity>, response: Response<SubEntity>) {
                    if (response?.body() != null) {
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

    fun getSellData(requestId: Int, userId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<SellEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getSellData(userId).enqueue(object : Callback<SellEntity> {
                override fun onFailure(call: Call<SellEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<SellEntity>, response: Response<SellEntity>) {
                    if (response?.body() != null) {
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


    fun getCheckData(requestId: Int, userId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CheckEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCheckData(userId).enqueue(object : Callback<CheckEntity> {
                override fun onFailure(call: Call<CheckEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CheckEntity>, response: Response<CheckEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }else{
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId,"接口出现错误")
                            mainView!!.hideLoading()
                        }
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


    fun getEvaData(requestId: Int, userId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<EvaEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getEvaData(userId).enqueue(object : Callback<EvaEntity> {
                override fun onFailure(call: Call<EvaEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<EvaEntity>, response: Response<EvaEntity>) {
                    if (response?.body() != null) {
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