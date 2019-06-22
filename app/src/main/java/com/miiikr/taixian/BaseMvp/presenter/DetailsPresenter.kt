package com.miiikr.taixian.BaseMvp.presenter

import com.miiikr.taixian.BaseMvp.IView.DetailsView
import com.miiikr.taixian.entity.CheckDetailsEntity
import com.miiikr.taixian.entity.CommonEntity
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

class DetailsPresenter : BasePresenter<DetailsView>() {


    private var mainView: DetailsView? = null

    fun attachView(view: DetailsView) {
        this.mainView = view
    }

    fun detachView() {
        this.mainView = null
    }

    fun isViewAttached(): Boolean {
        return mainView != null
    }


    fun getCheckDetailsInfo(requestId: Int, userId: String, productId: String, category: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CheckDetailsEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCheckDetailsInfo(userId, productId, category).enqueue(object : Callback<CheckDetailsEntity> {
                override fun onFailure(call: Call<CheckDetailsEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CheckDetailsEntity>, response: Response<CheckDetailsEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.onFailue(requestId,"")
                        mainView!!.hideLoading()
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

    fun getEvaDetailsInfo(requestId: Int, userId: String, productId: String, category: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CheckDetailsEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getEvaDetailsInfo(userId, productId, category).enqueue(object : Callback<CheckDetailsEntity> {
                override fun onFailure(call: Call<CheckDetailsEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CheckDetailsEntity>, response: Response<CheckDetailsEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.onFailue(requestId,"")
                        mainView!!.hideLoading()
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

    fun getSellDetailsInfo(requestId: Int, userId: String, productId: String, category: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CheckDetailsEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getSellDetailsInfo(userId, productId, category).enqueue(object : Callback<CheckDetailsEntity> {
                override fun onFailure(call: Call<CheckDetailsEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CheckDetailsEntity>, response: Response<CheckDetailsEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.onFailue(requestId,"")
                        mainView!!.hideLoading()
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


    fun confirmSellDetailsInfo(requestId: Int, userId: String, productId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.confirmSellDetailsInfo(userId, productId).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.onFailue(requestId,"")
                        mainView!!.hideLoading()
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