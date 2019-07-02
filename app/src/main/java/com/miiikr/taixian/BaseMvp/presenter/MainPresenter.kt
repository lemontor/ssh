package com.miiikr.taixian.BaseMvp.presenter

import android.content.Context
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.BaseMvp.IView.PersonView
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.MainEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import com.miiikr.taixian.net.RetrofitManager2
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainPresenter : BasePresenter<MainView>() {

    private var mainView: MainView? = null

    fun attachView(view: MainView) {
        this.mainView = view
    }

    fun detachView() {
        this.mainView = null
    }

    fun isViewAttached(): Boolean {
        return mainView != null
    }


    fun getMainData(requestId: Int) {
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<MainEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMainData().enqueue(object : Callback<MainEntity> {
                override fun onFailure(call: Call<MainEntity>, t: Throwable) {
                    if(isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<MainEntity>, response: Response<MainEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if(isViewAttached()){
                            mainView!!.hideLoading()

                        }
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestId, it)
                        mainView!!.hideLoading()
                    }

                }
    }

    fun setSexInfo(requestId: Int, userId: String, sex: String) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.setSex(userId, sex).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mView.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if(isViewAttached()){
                            mView.hideLoading()
                        }
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mView.onSuccess(requestId, it)
                    }
                }
    }

    fun getRes(context: Context):IntArray{
        val imgArray = context.resources.obtainTypedArray(R.array.animator)
        val len = imgArray.length()
        val resId = IntArray(len)
        for(index in 0 until len){
            resId[index] = imgArray.getResourceId(index, -1)
        }
        imgArray.recycle()
        return resId
    }

}