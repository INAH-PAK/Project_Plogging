package Network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
    companion object{
        fun getRetrofitInstans():Retrofit{

            val gson= GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://plogging.dothome.co.kr")
                .addConverterFactory(GsonConverterFactory.create(gson)).build()

            return retrofit
        }
    }
}