package fragments

import Network.RetrofitHelper
import Network.RetrofitService
import adapters.RecyclerAdaopterTab1
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.wookie_soft.inah.databinding.FragmentMainTab3Binding
import model.ScheduleVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Tab3MainFragment:Fragment(){



lateinit var  binding: FragmentMainTab3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTab3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchAPI()
    }
    private fun searchAPI(){
        val gson= GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.naver.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

        val call : Call<String> = retrofitService.naverNewsApi("플로깅",40,"sim" )
        call.enqueue( object  : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                //검색 성공
                Log.i("네이버 api 검색 결과" , response.body().toString())
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, "네트워크를 확인해주세요", Toast.LENGTH_SHORT).show()
            }

        })

    }
}