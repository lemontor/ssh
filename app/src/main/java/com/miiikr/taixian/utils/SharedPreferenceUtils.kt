package com.miiikr.taixian.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtils(context: Context) {

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    companion object {
        val PREFERENCE_NAME = "preference"
        val PREFERENCE_COOKIES_NAME = "cookies"
        val PREFERENCE_U_I = "I"
        val PREFERENCE_U_N = "N"
        val PREFERENCE_U_P = "P"
        val PREFERENCE_U_H = "H"
        val PREFERENCE_U_S = "S"
        val PREFERENCE_U_C = "isChose"


    }

    init {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, 0)
        editor = sharedPreferences!!.edit()
    }

    fun putStringSet(key: String, datas: MutableSet<String>) {
        editor!!.putStringSet(key, datas)
        editor!!.apply()
    }

    fun getCookies(key: String): MutableSet<String>? {
        return sharedPreferences!!.getStringSet(key, null)
    }

    fun putValue(key: String, value: String?):SharedPreferenceUtils {
        editor!!.putString(key, value)
        editor!!.apply()
        return this
    }

    fun putValueForInt(key: String, value: Int):SharedPreferenceUtils {
        editor!!.putInt(key, value)
        editor!!.apply()
        return this
    }

    fun getValueForInt(key: String):Int{
        return sharedPreferences!!.getInt(key,0)
    }


    fun getValue(key: String): String? {
        return sharedPreferences!!.getString(key, "")
    }

    fun isChose(key: String):Boolean{
        return sharedPreferences!!.getBoolean(key,false)
    }

    fun setChose(key:String,isChose:Boolean){
        editor!!.putBoolean(key,isChose)
        editor!!.apply()
    }












}