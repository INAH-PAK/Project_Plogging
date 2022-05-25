package com.wookie_soft.inah.network

import com.wookie_soft.inah.model.ScheduleVO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("pppDB.php")
    fun postMethodTest(@Body itemModel: ScheduleVO): Call<String>

}