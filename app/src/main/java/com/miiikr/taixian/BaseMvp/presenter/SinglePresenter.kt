package com.miiikr.taixian.BaseMvp.presenter

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.LoginEntity
import com.miiikr.taixian.entity.MoneyEntity
import com.miiikr.taixian.entity.ProductEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import com.miiikr.taixian.net.RetrofitManager2
import com.miiikr.taixian.utils.QRUtils
import com.miiikr.taixian.utils.SharedPreferenceUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SinglePresenter : BasePresenter<MainView>() {

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

    fun getProduct(requestId: Int) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<ProductEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getProduct().enqueue(object : Callback<ProductEntity> {
                override fun onFailure(call: Call<ProductEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<ProductEntity>, response: Response<ProductEntity>) {
                    if (response != null && response.body() != null) {
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
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                        mainView!!.hideLoading()
                    }
                }
    }


    fun createQR(requestId: Int, url: String, width: Int, height: Int) {
        Observable.create(ObservableOnSubscribe<Bitmap> {
            val bitmap = QRUtils.generateBitmapNoFrame(url, width, height)
            it.onNext(bitmap!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
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

    fun getView(requestId: Int,view:View) {
        Observable.create(ObservableOnSubscribe<Bitmap> {
               val createView = QRUtils.getViewShot(view)
               it.onNext(createView!!)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
                }
    }

    fun getMoneyData(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<MoneyEntity> {
                val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyData(userId).enqueue(object :Callback<MoneyEntity>{
                override fun onFailure(call: Call<MoneyEntity>, t: Throwable) {
                       if(isViewAttached()){
                           mainView!!.onFailue(requestId,t.message!!)
                           mainView!!.hideLoading()
                       }
                }

                override fun onResponse(call: Call<MoneyEntity>, response: Response<MoneyEntity>) {
                    if(response?.body()!=null){
                        it.onNext(response.body()!!)
                    }else{
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }

    fun advice(requestId: Int,userId:String,content:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.addAdvice(userId,content).enqueue(object :Callback<CommonEntity>{
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if(response?.body()!=null){
                        it.onNext(response.body()!!)
                    }else{
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }




}