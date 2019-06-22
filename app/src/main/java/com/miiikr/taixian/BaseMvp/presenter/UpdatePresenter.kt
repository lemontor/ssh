package com.miiikr.taixian.BaseMvp.presenter

import android.content.Context
import android.text.TextUtils
import com.miiikr.taixian.BaseMvp.IView.MainView
import com.miiikr.taixian.R
import com.miiikr.taixian.entity.*
import com.miiikr.taixian.net.RetrofitApiInterface
import com.miiikr.taixian.net.RetrofitManager
import com.miiikr.taixian.net.RetrofitManager2
import com.miiikr.taixian.utils.ToastUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File

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
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
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
            val data = PicEntity(1, datas)
            it.onNext(data)
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (isViewAttached()) {
                        mainView!!.onSuccess(requestId, it)
                    }
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

    fun getBrand(requestId: Int, typeId: String) {
        if (isViewAttached()) {
            mainView!!.showLoading()
        }
        Observable.create(ObservableOnSubscribe<BrandEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.getBrand(typeId).enqueue(object : Callback<BrandEntity> {
                override fun onFailure(call: Call<BrandEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<BrandEntity>, response: Response<BrandEntity>) {
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
                        mainView!!.hideLoading()
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

    fun uploadFile(requestId: Int, file: File) {
        Observable.create(ObservableOnSubscribe<UploadEntity> {
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val part = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val api = RetrofitManager2.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.uploadImage(part).enqueue(object : Callback<UploadEntity> {
                override fun onFailure(call: Call<UploadEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<UploadEntity>, response: Response<UploadEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
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

    fun uploadInfoForSell(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                   oldAndNewState: String, productState: String, watchMaterial: String, watchStyle: String, annexExplain: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
//                if(isSell == 1){
            api.updateSellWatchInfo(userId, brandId, categoryId, userExplain, oldAndNewState,
                    productState, watchStyle, annexExplain, watchMaterial,
                    pics[0]!!, pics[1]!!, pics[2]!!, pics[3]!!, pics[4]!!, pics[5]!!).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        mainView!!.onFailue(requestId,"")
                        mainView!!.hideLoading()
                    }
                }
            })
//                }else{
//                    api.updateCheckWatchInfo(userId,brandId,categoryId,userExplain,oldAndNewState,productState,watchStyle,annexExplain,watchMaterial, pics[0]!!,pics[1]!!,pics[2]!!,pics[3]!!,pics[4]!!,pics[5]!!)
//                }
//            a
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if(isViewAttached()){
                        mainView!!.onSuccess(requestId,it)
                        mainView!!.hideLoading()
                    }
                }
    }

    fun uploadInfoForCheck(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                   oldAndNewState: String, productState: String, watchMaterial: String, watchStyle: String, annexExplain: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updateCheckWatchInfo(userId, brandId, categoryId, userExplain, oldAndNewState,
                    productState, watchStyle, annexExplain, watchMaterial,
                    pics[0]!!, pics[1]!!, pics[2]!!, pics[3]!!, pics[4]!!, pics[5]!!).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
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


    fun checkInfo(context: Context, oldAndNewState: String, productState: String, watchStyle: String, watchMaterial: String, pics: HashMap<Int, File>): Boolean {
        if (TextUtils.isEmpty(oldAndNewState)) {
            ToastUtils.toastShow(context, "请选择产品新旧程度")
            return false
        }
        if (TextUtils.isEmpty(productState)) {
            ToastUtils.toastShow(context, "请确认产品功能是否正常")
            return false
        }
        if (TextUtils.isEmpty(watchStyle)) {
            ToastUtils.toastShow(context, "请选择产品款式")
            return false
        }
        if (TextUtils.isEmpty(watchMaterial)) {
            ToastUtils.toastShow(context, "请选择手表材质")
            return false
        }
        if (pics.size < 6) {
            if (!pics.containsKey(0)) {
                ToastUtils.toastShow(context, "请选择整体照")
                return false
            }

            if (!pics.containsKey(1)) {
                ToastUtils.toastShow(context, "请选择logo细节照")
                return false
            }
            if (!pics.containsKey(2)) {
                ToastUtils.toastShow(context, "请选择中心轴细节照")
                return false
            }
            if (!pics.containsKey(3)) {
                ToastUtils.toastShow(context, "请选择指针细节照")
                return false
            }
            if (!pics.containsKey(4)) {
                ToastUtils.toastShow(context, "请选择指针细节照")
                return false
            }
            if (!pics.containsKey(5)) {
                ToastUtils.toastShow(context, "请选择耳标细节照")
                return false
            }

        }

        return true
    }


    fun getFuncChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_func_basic), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_func_lost), false))
        return datas
    }

    fun getMaterialChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_material_golden), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_material_steel), false))
        return datas
    }

    fun getMaterialJewelryChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_pt950), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_au750), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_material_925), false))
        return datas
    }


    fun getStyleChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_man), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_women), false))
        return datas
    }

    fun getDiamendChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_org_diamond), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.jewelry_last_diamond), false))
        return datas
    }

    fun getSizeChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_mini), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_big), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_medium), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.bag_size_small), false))
        return datas
    }


    fun getFileChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_man), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_style_women), false))
        return datas
    }


}