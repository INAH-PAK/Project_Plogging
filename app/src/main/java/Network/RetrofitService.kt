package Network

import model.Item
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("TestPlogging/insertDB.php")
    fun postCalenderDataToServer(
        @Body item: ArrayList<Item>
    ) :Call<ArrayList<Item>>




}