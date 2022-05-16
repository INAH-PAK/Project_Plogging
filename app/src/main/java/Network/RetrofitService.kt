package Network

import model.Item
import model.ScheduleVO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("TestPlogging/insertDB.php")
    fun postMethodTest(@Body itemModel:ScheduleVO): Call<ScheduleVO>

}