package Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {


    companion object {

    fun getRetrofitInstance(): Retrofit? { // 객체를 안만들고도 사용 가능
        val builder = Retrofit.Builder()
        builder.baseUrl("http://webserver.dothome.co.kr")
        builder.addConverterFactory(GsonConverterFactory.create())
        return builder.build()
    }

    }
}