package com.miiikr.taixian.BaseMvp.presenter

import android.content.Context
import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.BaseMvp.IView.RecoverView
import com.miiikr.taixian.entity.CommonEntity
import com.miiikr.taixian.entity.GemmologistEntity
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager2
import com.miiikr.taixian.utils.ToastUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecoverPersenter : BasePresenter<RecoverView>() {

    private var mainView: RecoverView? = null

    fun attachView(view: RecoverView) {
        this.mainView = view
    }

    fun detachView() {
        this.mainView = null
    }

    fun isViewAttached(): Boolean {
        return mainView != null
    }



    fun recoverForStoryMethodOne(requestInt: Int,userId:String,productId:String,name:String,phone:String,sex:String,reservationTime:String,gemmologistId:String,recoveryType:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
             val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.recoverMothodForStore(userId,productId,name,phone,sex,reservationTime,gemmologistId,recoveryType).enqueue(object :Callback<CommonEntity>{
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestInt,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                   if (response?.body() != null){
                       it.onNext(response.body()!!)
                   }else{
                       mainView!!.hideLoading()
                   }
                }
            })

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mainView!!.onSuccess(requestInt,it)
                    mainView!!.hideLoading()
                }
    }


    fun recoverForStoryMethodOne(requestInt: Int,userId:String,productId:String,recoveryType:String,expressId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.recoverMothodForDelivery(userId,productId,recoveryType,expressId).enqueue(object :Callback<CommonEntity>{
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestInt,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null){
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.hideLoading()
                    }
                }
            })

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mainView!!.onSuccess(requestInt,it)
                    mainView!!.hideLoading()
                }
    }


    fun getGemmologistData(requestInt: Int){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe <GemmologistEntity>{
                val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getGemmologistData().enqueue(object :Callback<GemmologistEntity>{
                override fun onFailure(call: Call<GemmologistEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestInt,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<GemmologistEntity>, response: Response<GemmologistEntity>) {
                    if(response?.body() != null){
                        it.onNext(response.body()!!)
                    }else{
                        mainView!!.hideLoading()
                    }
                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestInt,it)
                        mainView!!.hideLoading()
                    }
                }
    }







    fun  checkInfo(context: Context,name: String,sex: String,phone: String,time:String):Boolean{
         if (name == ""){
             ToastUtils.toastShow(context,"请填写姓名")
             return false
         }

        if (sex == ""){
            ToastUtils.toastShow(context,"请选择性别")
            return false
        }

        if (phone == ""){
            ToastUtils.toastShow(context,"请填写手机号")
            return false
        }

        if (name == ""){
            ToastUtils.toastShow(context,"请选择预约时间")
            return false
        }

        return true

    }





}