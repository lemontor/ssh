package com.miiikr.taixian.net

import com.miiikr.taixian.entity.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitApiInterface {



    @FormUrlEncoded
    @POST("api/userRegisterInfo/sendSmsCode")
    fun  getCode(@Field("phone") phone:String): Call<CommonEntity>


    @FormUrlEncoded
    @POST("api/userRegisterInfo/smsRegister")
    fun  doLogin(@Field("phone") phone: String,@Field("verifyCode") verifyCode:String):Call<LoginEntity>


    @POST("api/homepageRelease/allInfo")
    fun  getMainData(): Call<MainEntity>


    @FormUrlEncoded
    @POST("api/sshDengLuUser/changeHeadSex")
    fun  setSex(@Field("userId")userId:String,@Field("sex")sex:String):Call<CommonEntity>


    @POST("api/sshProductSell/selectBrandInfoClaas")
    fun   getProduct():Call<ProductEntity>

    @POST("api/sshProductSell/selectBrandSell")
    fun   getBrand(@Field("productId")productId:String):Call<BrandEntity>







}