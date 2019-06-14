package com.miiikr.taixian.net

import com.miiikr.taixian.entity.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {


    @FormUrlEncoded
    @POST("api/userRegisterInfo/sendSmsCode")
    fun getCode(@Field("phone") phone: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/userRegisterInfo/smsRegister")
    fun doLogin(@Field("phone") phone: String, @Field("verifyCode") verifyCode: String): Call<LoginEntity>

    @POST("api/homepageRelease/allInfo")
    fun getMainData(): Call<MainEntity>

    @FormUrlEncoded
    @POST("api/sshDengLuUser/changeHeadSex")
    fun setSex(@Field("userId") userId: String, @Field("sex") sex: String): Call<CommonEntity>

    @POST("api/sshProductSell/selectBrandInfoClaas")
    fun getProduct(): Call<ProductEntity>

    @POST("api/sshProductSell/selectBrandSell")
    fun getBrand(@Field("productId") productId: String): Call<BrandEntity>

    @Multipart
    @POST("api/sshProductSell/uploadImg")
    fun uploadImage(@Part() file: MultipartBody.Part): Call<UploadEntity>

    @FormUrlEncoded
    @POST("api/sshDengLuUser/userinfo")
    fun getUserInfo(@Field("userId") useId: String): Call<LoginEntity>

    @FormUrlEncoded
    @POST("api/sshDengLuUser/nickname")
    fun updateNickName(@Field("userId") useId: String, @Field("nickName") nickName: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshDengLuUser/phone")
    fun updatePhone(@Field("userId") useId: String, @Field("phone") phone: String, @Field("verifyCode") verifyCode: String): Call<CommonEntity>

    @Multipart
    @POST("api/sshDengLuUser/headportrait")
    fun updateHeadPic(@Part file: MultipartBody.Part, @Part("userId") useId: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshReservation/reservationList")
    fun getSubData(@Field("userId") useId: String):Call<SubEntity>

    @FormUrlEncoded
    @POST("api/sshUserSales/salesList")
    fun getSellData(@Field("userId") useId: String):Call<SellEntity>

    @FormUrlEncoded
    @POST("api/sshAppraisal/appraisalList")
    fun getCheckData(@Field("userId") useId: String):Call<CheckEntity>

    @FormUrlEncoded
    @POST("api/sshEvaluation/evaluationList")
    fun getEvaData(@Field("userId") useId: String):Call<EvaEntity>

    @FormUrlEncoded
    @POST("api/sshReservation/changeReservationState")
    fun getEvaDataCancel(@Field("userId") useId: String,@Field("productId")productId:String,@Field("state")state:Int):Call<CommonEntity>



}