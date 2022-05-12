package Network

import model.ItemVO
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("TestPlogging/insertDB.php")
    fun postCalenderDataToServer(
        @Body item: ArrayList<ItemVO>
    ) :Call<ArrayList<ItemVO>>




}