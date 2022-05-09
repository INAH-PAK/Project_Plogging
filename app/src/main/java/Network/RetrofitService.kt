package Network

import model.ItemCalenderVO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @POST("TestPlogging/insertDB.php")
    fun postCalenderDataToServer(
        @Body item: ArrayList<ItemCalenderVO>
    ) :Call<ArrayList<ItemCalenderVO>>




}