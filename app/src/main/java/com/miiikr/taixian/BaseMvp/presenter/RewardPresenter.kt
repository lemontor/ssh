package com.miiikr.taixian.BaseMvp.presenter

import com.miiikr.taixian.BaseMvp.IView.AccountView
import com.miiikr.taixian.entity.*
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RewardPresenter : BasePresenter<AccountView>() {

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

    fun getMoneyData(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<MoneyStateEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyState(userId).enqueue(object : Callback<MoneyStateEntity> {
                override fun onFailure(call: Call<MoneyStateEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<MoneyStateEntity>, response: Response<MoneyStateEntity>) {
                    if(response?.body()!=null){
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }


    fun getMoney(requestId: Int,userId:String,bonusesId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyToPackage(userId,bonusesId).enqueue(object : Callback<CommonEntity> {
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

    fun getCash(requestId: Int,userId:String,bonusesId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCash(userId,bonusesId).enqueue(object : Callback<CommonEntity> {
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


    fun getMoneyForComplete(requestId: Int,userId:String){
        Observable.create(ObservableOnSubscribe<MoneyCompleteEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyForComplete(userId).enqueue(object : Callback<MoneyCompleteEntity> {
                override fun onFailure(call: Call<MoneyCompleteEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                    }
                }

                override fun onResponse(call: Call<MoneyCompleteEntity>, response: Response<MoneyCompleteEntity>) {
                    if(response?.body()!=null){
                        it.onNext(response.body()!!)
                    }else{
                    }

                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestId,it)
                    }
                }
    }

    fun getMoneyForWait(requestId: Int,userId:String){
        Observable.create(ObservableOnSubscribe<MoneyCompleteEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyForWait(userId).enqueue(object : Callback<MoneyCompleteEntity> {
                override fun onFailure(call: Call<MoneyCompleteEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                    }
                }

                override fun onResponse(call: Call<MoneyCompleteEntity>, response: Response<MoneyCompleteEntity>) {
                    if(response?.body()!=null){
                        it.onNext(response.body()!!)
                    }else{
                    }

                }
            })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestId,it)
                    }
                }
    }

    fun getMoneyCompleteList(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<MoneyStateEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyCompleteList(userId).enqueue(object : Callback<MoneyStateEntity> {
                override fun onFailure(call: Call<MoneyStateEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<MoneyStateEntity>, response: Response<MoneyStateEntity>) {
                    if(response?.body()!=null){
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }

    fun getMoneyWaiteList(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<MoneyStateEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getMoneyWaiteList(userId).enqueue(object : Callback<MoneyStateEntity> {
                override fun onFailure(call: Call<MoneyStateEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<MoneyStateEntity>, response: Response<MoneyStateEntity>) {
                    if(response?.body()!=null){
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }

    fun getCashDetails(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CashEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCashInfo(userId).enqueue(object : Callback<CashEntity> {
                override fun onFailure(call: Call<CashEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CashEntity>, response: Response<CashEntity>) {
                    if(response?.body()!=null){
                        it.onNext(response.body()!!)
                    }else{
                        if(isViewAttached()) {
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

    fun getCashListDetails(requestId: Int,userId:String){
        if(isViewAttached()){
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<CashDetailsEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getCashDetails(userId).enqueue(object : Callback<CashDetailsEntity> {
                override fun onFailure(call: Call<CashDetailsEntity>, t: Throwable) {
                    if(isViewAttached()){
                        mainView!!.onFailue(requestId,t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CashDetailsEntity>, response: Response<CashDetailsEntity>) {
                    if(response?.body()!=null){
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
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }

}