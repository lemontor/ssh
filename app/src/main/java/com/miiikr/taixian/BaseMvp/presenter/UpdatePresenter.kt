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

    fun getPicDataForJ(requestId: Int) {
        Observable.create(ObservableOnSubscribe<PicEntity> {
            var datas = ArrayList<PicEntity.PicData>()
            val pic1 = PicEntity.PicData("", "整体照", R.mipmap.icon_top_left)
            val pic2 = PicEntity.PicData("", "正面图", R.mipmap.icon_center_top)
            val pic3 = PicEntity.PicData("", "底部图", R.mipmap.icon_top_right)
            val pic4 = PicEntity.PicData("", " LOGO图", R.mipmap.icon_bottom_left)
            val pic5 = PicEntity.PicData("", " 内圈刻印", R.mipmap.icon_center_bottom)
            val pic6 = PicEntity.PicData("", "补充#1", R.mipmap.icon_bottom_right)
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
                        if (isViewAttached()) {
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
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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

    /*

     @FormUrlEncoded
    @POST("api/sshProductSell/saveSalesProductInfo")
    fun updateSellWatchInfoWithBag(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                            @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String,
                             @Field("annexExplain") annexExplain: String, @Field("bagsSize") bagsSize  : String,
                            @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                            , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String
    ): Call<CommonEntity>




     */

    fun uploadInfoForSellWithBag(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                                 oldAndNewState: String, annexExplain: String, bagsSize: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
//                if(isSell == 1){
            api.updateSellWatchInfoWithBag(userId, brandId, categoryId, userExplain, oldAndNewState,
                    annexExplain, bagsSize,
                    pics[0]!!, pics[1]!!, pics[2]!!, pics[3]!!, pics[4]!!, if (pics.size == 6) {
                pics[5]
            } else {
                ""
            }).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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

    fun uploadInfoForCheckWithBag(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                                  oldAndNewState: String, annexExplain: String, bagsSize: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
//                if(isSell == 1){
            api.updateCheckWatchInfoWithBag(userId, brandId, categoryId, userExplain, oldAndNewState,
                    annexExplain, bagsSize,
                    pics[0]!!, pics[1]!!, pics[2]!!, pics[3]!!, pics[4]!!, if (pics.size == 6) {
                pics[5]
            } else {
                ""
            }).enqueue(object : Callback<CommonEntity> {
                override fun onFailure(call: Call<CommonEntity>, t: Throwable) {
                    if (isViewAttached()) {
                        mainView!!.onFailue(requestId, t.message!!)
                        mainView!!.hideLoading()
                    }
                }

                override fun onResponse(call: Call<CommonEntity>, response: Response<CommonEntity>) {
                    if (response?.body() != null) {
                        it.onNext(response.body()!!)
                    } else {
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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


    fun uploadInfoForSellWithJ(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                               oldAndNewState: String, annexExplain: String, jewelryDiamond: String, jewelryMaterial: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updateSellWatchInfoWithJewelry(userId, brandId, categoryId, userExplain, oldAndNewState,
                    annexExplain, jewelryDiamond, jewelryMaterial,
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
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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

    fun uploadInfoForCheckWithJ(requestId: Int, userId: String, brandId: String, categoryId: String, userExplain: String,
                                oldAndNewState: String, annexExplain: String, jewelryDiamond: String, jewelryMaterial: String, pics: HashMap<Int, String>) {
        Observable.create(ObservableOnSubscribe<CommonEntity> {
            val api = RetrofitManager.initRetrofit()!!.create(RetrofitApiInterface::class.java)
            api.updateCheckWatchInfoWithJewelry(userId, brandId, categoryId, userExplain, oldAndNewState,
                    annexExplain, jewelryDiamond, jewelryMaterial,
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
                        if (isViewAttached()) {
                            mainView!!.onFailue(requestId, "")
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
        if (pics.size == 6) {
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

    fun checkBagInfo(context: Context, brand: String, size: String, file: String, oldAndNewState: String, pics: HashMap<Int, File>): Boolean {

        if (TextUtils.isEmpty(brand)) {
            ToastUtils.toastShow(context, "请选择包包品牌")
            return false
        }

        if (TextUtils.isEmpty(size)) {
            ToastUtils.toastShow(context, "请选择包包大小")
            return false
        }

        if (TextUtils.isEmpty(file)) {
            ToastUtils.toastShow(context, "请选择附件")
            return false
        }

        if (TextUtils.isEmpty(oldAndNewState)) {
            ToastUtils.toastShow(context, "请选择产品新旧程度")
            return false
        }

        if (pics.size == 6 || pics.size == 5) {
            if (!pics.containsKey(0)) {
                ToastUtils.toastShow(context, "请选择整体照")
                return false
            }

            if (!pics.containsKey(1)) {
                ToastUtils.toastShow(context, "请选择正面照")
                return false
            }
            if (!pics.containsKey(2)) {
                ToastUtils.toastShow(context, "请选择底部照")
                return false
            }
            if (!pics.containsKey(3)) {
                ToastUtils.toastShow(context, "请选择内圈刻印照")
                return false
            }
            if (!pics.containsKey(4)) {
                ToastUtils.toastShow(context, "请选择LOGO照")
                return false
            }
//            if (!pics.containsKey(5)) {
//                ToastUtils.toastShow(context, "请选择耳标细节照")
//                return false
//            }

        }

        return true
    }

    fun checkBagInfoJewelry(context: Context, brand: String, material: String, diamond: String, file: String, pics: HashMap<Int, File>): Boolean {

        if (TextUtils.isEmpty(brand)) {
            ToastUtils.toastShow(context, "请选择首饰品牌")
            return false
        }

        if (TextUtils.isEmpty(material)) {
            ToastUtils.toastShow(context, "请选择首饰材质")
            return false
        }

        if (TextUtils.isEmpty(diamond)) {
            ToastUtils.toastShow(context, "请选择首饰是否镶钻")
            return false
        }

        if (TextUtils.isEmpty(file)) {
            ToastUtils.toastShow(context, "请选择首饰附件")
            return false
        }

        if (pics.size == 6) {
            if (!pics.containsKey(0)) {
                ToastUtils.toastShow(context, "请选择整体照")
                return false
            }

            if (!pics.containsKey(1)) {
                ToastUtils.toastShow(context, "请选择LOGO细节照")
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
                ToastUtils.toastShow(context, "请选择刻度细节照")
                return false
            }
//            if (!pics.containsKey(5)) {
//                ToastUtils.toastShow(context, "请选择手表刻度细节照")
//                return false
//            }
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


    fun getFileWatchChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_one), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_two), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_three), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_four), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_five), false))
        return datas
    }

    fun getFileBagChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_one), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_six), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_three), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_files_four), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_seven), false))
        return datas
    }

    fun getFileJewelryChose(context: Context): ArrayList<ChoseEntity> {
        val datas = ArrayList<ChoseEntity>()
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_a), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_b), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_c), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_d), false))
        datas.add(ChoseEntity(context.resources.getString(R.string.chose_goods_notify_e), false))
        return datas
    }



}