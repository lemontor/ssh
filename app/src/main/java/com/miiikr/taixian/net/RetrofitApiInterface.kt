package com.miiikr.taixian.net

import com.miiikr.taixian.entity.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {


    @FormUrlEncoded
    @POST("api/userRegisterInfo/sendSmsCode")
    fun getCode(@Field("phone") phone: String): Call<CommonBody>

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

    @FormUrlEncoded
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
    fun getSubData(@Field("userId") useId: String): Call<SubEntity>

    @FormUrlEncoded
    @POST("api/sshUserSales/salesList")
    fun getSellData(@Field("userId") useId: String): Call<SellEntity>

    @FormUrlEncoded
    @POST("api/sshAppraisal/appraisalList")
    fun getCheckData(@Field("userId") useId: String): Call<CheckEntity>

    @FormUrlEncoded
    @POST("api/sshEvaluation/evaluationList")
    fun getEvaData(@Field("userId") useId: String): Call<EvaEntity>

    @FormUrlEncoded
    @POST("api/sshReservation/changeReservationState")
    fun getEvaDataCancel(@Field("userId") useId: String, @Field("productId") productId: String, @Field("state") state: Int): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshUserSales/deleteSales")
    fun cancelSell(@Field("userId") useId: String, @Field("productId") productId: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshReservation/reservation")
    fun getSubData(@Field("userId") useId: String, @Field("productId") productId: String): Call<SubEntity>


    /*
    闲置出售
     */
    @FormUrlEncoded
    @POST("api/sshProductSell/saveSalesProductInfo")
    fun updateSellWatchInfo(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                            @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String, @Field("productState") productState: String,
                            @Field("watchStyle ") watchStyle: String, @Field("annexExplain") annexExplain: String, @Field("watchMaterial") watchMaterial: String,
                            @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                            , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String
    ): Call<CommonEntity>

    /*
    闲置检查
     */
    @FormUrlEncoded
    @POST("api/sshProductIdentify/saveProductIdentify")
    fun updateCheckWatchInfo(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                             @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String, @Field("productState") productState: String,
                             @Field("watchStyle ") watchStyle: String, @Field("annexExplain") annexExplain: String, @Field("watchMaterial") watchMaterial: String,
                             @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                             , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String
    ): Call<CommonEntity>


    @FormUrlEncoded
    @POST("api/sshProductSell/saveSalesProductInfo")
    fun updateSellWatchInfoWithBag(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                            @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String,
                             @Field("annexExplain") annexExplain: String, @Field("bagsSize") bagsSize  : String,
                            @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                            , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String?): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshProductIdentify/saveProductIdentify")
    fun updateCheckWatchInfoWithBag(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                                   @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String,
                                   @Field("annexExplain") annexExplain: String, @Field("bagsSize") bagsSize  : String,
                                   @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                                   , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String?): Call<CommonEntity>






    @FormUrlEncoded
    @POST("api/sshProductSell/saveSalesProductInfo")
    fun updateSellWatchInfoWithJewelry(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                                   @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String,
                                   @Field("annexExplain") annexExplain: String, @Field("jewelryDiamond") jewelryDiamond  : String,@Field("jewelryMaterial") jewelryMaterial  : String,
                                   @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                                   , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String
    ): Call<CommonEntity>


    @FormUrlEncoded
    @POST("api/sshProductIdentify/saveProductIdentify")
    fun updateCheckWatchInfoWithJewelry(@Field("userId") userId: String, @Field("brandId") brandId: String, @Field("categoryId") categoryId: String,
                                       @Field("userExplain") userExplain: String, @Field("oldAndNewState") oldAndNewState: String,
                                       @Field("annexExplain") annexExplain: String, @Field("jewelryDiamond") jewelryDiamond  : String,@Field("jewelryMaterial") jewelryMaterial  : String,
                                       @Field("leftUpperImg") leftUpperImg: String, @Field("middleUpperImg") middleUpperImg: String, @Field("rightUpperImg") rightUpperImg: String
                                       , @Field("leftLowerImg") leftLowerImg: String, @Field("middleLowerImg") middleLowerImg: String, @Field("rightLowerImg") rightLowerImg: String
    ): Call<CommonEntity>




    @FormUrlEncoded
    @POST("api/sshAppraisal/appraisalDetail")
    fun getCheckDetailsInfo(@Field("userId") userId: String, @Field("productId") productId: String, @Field("categoryId") categoryId: String): Call<CheckDetailsEntity>


    @FormUrlEncoded
    @POST("api/sshEvaluation/evaluationDetail")
    fun getEvaDetailsInfo(@Field("userId") userId: String, @Field("productId") productId: String, @Field("categoryId") categoryId: String): Call<CheckDetailsEntity>


    @FormUrlEncoded
    @POST("api/sshUserSales/salesDetail")
    fun getSellDetailsInfo(@Field("userId") userId: String, @Field("productId") productId: String, @Field("categoryId") categoryId: String): Call<CheckDetailsEntity>


    @FormUrlEncoded
    @POST("api/sshEvaluation/confirmEvaluation")
    fun confirmSellDetailsInfo(@Field("userId") userId: String, @Field("productId") productId: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshUserSales/addSalesModeOne")
    fun recoverMothodForStore(@Field("userId") userId: String, @Field("productId") productId: String, @Field("name") name: String
                              , @Field("phone") phone: String, @Field("sex") sex: String, @Field("reservationTime") reservationTime: String
                              , @Field("gemmologistId") gemmologistId: String, @Field("recoveryType") recoveryType: String): Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/sshUserSales/addSalesModeTwo")
    fun recoverMothodForDelivery(@Field("userId") userId: String, @Field("productId") productId: String, @Field("recoveryType") recoveryType: String, @Field("expressId") expressId: String): Call<CommonEntity>


    @POST("api/sshGemmologist/gemmologistList")
    fun getGemmologistData(): Call<GemmologistEntity>

    @FormUrlEncoded
    @POST("api/WxLand/landPreparation")
    fun pushCode(@Field("code")code:String):Call<WxLoginEntity>

    @FormUrlEncoded
    @POST("api/WxLand/land")
    fun wxLoginNoPhone(@Field("targetId")targetId:String,@Field("mode")mode:String):Call<LoginEntity>

    @FormUrlEncoded
    @POST("api/WxLand/land")
    fun wxLoginHasPhone(@Field("targetId")targetId:String,@Field("mode")mode:String,
                        @Field("phone")phone:String,@Field("verifyCode")verifyCode:String):Call<LoginEntity>

    @FormUrlEncoded
    @POST("api/inviteReward/queryInviteNumberAndBonuses")
    fun getMoneyData(@Field("userId") userId: String):Call<MoneyEntity>

    @FormUrlEncoded
    @POST("api/inviteReward/selectUserAllBonuses")
    fun getMoneyState(@Field("userId")userId:String):Call<MoneyStateEntity>

    @FormUrlEncoded
    @POST("api/inviteReward/userObtainBonuses")
    fun getMoneyToPackage(@Field("userId")userId:String,@Field("bonusesId")bonusesId:String):Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/myWallet/completeTransaction")
    fun getMoneyForComplete(@Field("userId")userId:String):Call<MoneyCompleteEntity>

    @FormUrlEncoded
    @POST("api/myWallet/waitingTransaction")
    fun getMoneyForWait(@Field("userId")userId:String):Call<MoneyCompleteEntity>

    @FormUrlEncoded
    @POST(" api/myWallet/completeTransactionList")
    fun getMoneyCompleteList(@Field("userId")userId:String):Call<MoneyStateEntity>

    @FormUrlEncoded
    @POST(" api/myWallet/waitingTransactionList")
    fun getMoneyWaiteList(@Field("userId")userId:String):Call<MoneyStateEntity>

    @FormUrlEncoded
    @POST("api/myWallet/ObtainCompleteTransactionBonuses")
    fun getCash(@Field("userId")userId:String,@Field("bonusesId")bonusesId:String):Call<CommonEntity>

    @FormUrlEncoded
    @POST("api/myWallet/queryUserMoney")
    fun getCashInfo(@Field("userId")userId:String):Call<CashEntity>

    @FormUrlEncoded
    @POST("api/myWallet/queryMoneyDetails")
    fun getCashDetails(@Field("userId")userId:String):Call<CashDetailsEntity>

    @FormUrlEncoded
    @POST("api/sshUserFeedback/addUserFeedback")
    fun addAdvice(@Field("userId")userId:String,@Field("feedbackInfo")feedbackInfo:String):Call<CommonEntity>



}