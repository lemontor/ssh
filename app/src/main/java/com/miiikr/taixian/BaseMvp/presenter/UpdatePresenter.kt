package com.miiikr.taixian.BaseMvp.presenter

import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.BrandEntity
import com.miiikr.taixian.entity.PicEntity
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

class UpdatePresenter : BasePresenter<MainView>() {

    fun getPicDataForWatch(requestId: Int) {
        Observable.create(ObservableOnSubscribe<PicEntity> {
            var datas = ArrayList<PicEntity.PicData>()
            val pic1 = PicEntity.PicData("", "整体照", R.mipmap.icon_watch_main)
            val pic2 = PicEntity.PicData("", "logo细节", R.mipmap.icon_watch_logo)
            val pic3 = PicEntity.PicData("", "中心轴细节", R.mipmap.icon_watch_center)
            val pic4 = PicEntity.PicData("", "指针细节", R.mipmap.icon_watch_point)
            val pic5 = PicEntity.PicData("", "手表刻度细节", R.mipmap.icon_watch_graduation)
            val pic6 = PicEntity.PicData("", "耳标细节", R.mipmap.icon_watch_ear)
            datas.add(pic1)
            datas.add(pic2)
            datas.add(pic3)
            datas.add(pic4)
            datas.add(pic5)
            datas.add(pic6)
            val data = PicEntity(0, datas)
            it.onNext(data)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView.onSuccess(requestId, it)
                }
    }

    fun getPicDataForBag(requestId: Int) {
        Observable.create(ObservableOnSubscribe<PicEntity> {
            var datas = ArrayList<PicEntity.PicData>()
            val pic1 = PicEntity.PicData("", "整体照", R.mipmap.icon_bag_main)
            val pic2 = PicEntity.PicData("", "正面图", R.mipmap.icon_bag_front)
            val pic3 = PicEntity.PicData("", "底部图", R.mipmap.icon_bag_bottom)
            val pic4 = PicEntity.PicData("", "内圈刻印", R.mipmap.icon_bag_mark)
            val pic5 = PicEntity.PicData("", "LOGO图", R.mipmap.icon_bag_logo)
            val pic6 = PicEntity.PicData("", "补充#1", R.mipmap.icon_bag_add)
            datas.add(pic1)
            datas.add(pic2)
            datas.add(pic3)
            datas.add(pic4)
            datas.add(pic5)
            datas.add(pic6)
            val data = PicEntity(0, datas)
            it.onNext(data)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mView.onSuccess(requestId, it)
                }
    }


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

    fun getBrand(requestId: Int,typeId:String) {
        Observable.create(ObservableOnSubscribe<BrandEntity> {
              val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
             api.getBrand(typeId).enqueue(object :Callback<BrandEntity>{
                 override fun onFailure(call: Call<BrandEntity>, t: Throwable) {
                     if (isViewAttached()){
                         mainView!!.onFailue(requestId,t.message!!)
                     }
                 }

                 override fun onResponse(call: Call<BrandEntity>, response: Response<BrandEntity>) {
                     it.onNext(response.body()!!)
                 }

             })
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()){
                        mainView!!.onSuccess(requestId,it)
                    }
                }
    }


}