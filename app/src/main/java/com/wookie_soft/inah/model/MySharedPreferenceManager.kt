package com.wookie_soft.inah.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object MySharedPreferenceManager {
    private const val EMAIL = "EMAIL"
    private lateinit var preference: SharedPreferences

    //아 init으로 context받기 가능구
    fun init(context: Context){
        preference =context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    // 이메일값을 겟셋으로 쓰고 얻기 ~
    var userEmail:String
    get() = preference.getString(EMAIL,"")?:""
    set(userEmail) = preference.edit {
        putString(EMAIL, userEmail)
    }



}