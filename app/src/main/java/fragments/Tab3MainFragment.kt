package fragments

import Network.RetrofitHelper
import Network.RetrofitService
import adapters.RecyclerAdaopterTab1
import adapters.RecyclerFragmentAdapterTap3
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.wookie_soft.inah.databinding.FragmentMainTab3Binding
import model.NaverApiResponse
import model.ScheduleVO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Tab3MainFragment:Fragment(){

lateinit var recyclerView: RecyclerView
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

    override fun onResume() {
        super.onResume()
        searchAPI()
    }
    private fun searchAPI(){
        val gson= GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://openapi.naver.com")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()

        val retrofitService = retrofit.create(RetrofitService::class.java)

                val call : Call<NaverApiResponse> = retrofitService.naverNewsApi("플로깅",40,"sim" )
        call.enqueue( object  : Callback<NaverApiResponse>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<NaverApiResponse>, response: Response<NaverApiResponse>) {
                //검색 성공
                if ( response.body() == null ) return
                else{
                    Log.i("네이버 api 검색 결과" , response.body().toString())
                    val data = response.body()!!
                    Log.i(" 그 중 아이템들 ", data.items[0].title)
                    binding.recyclerNews.adapter= RecyclerFragmentAdapterTap3(requireContext(),data.items)
                    binding.recyclerNews.adapter?.notifyDataSetChanged()


                }

            }

            override fun onFailure(call: Call<NaverApiResponse>, t: Throwable) {

                Toast.makeText(context, "네트워크를 확인해주세요", Toast.LENGTH_SHORT).show()
                Log.i("tt??????????????????"," 호옹 $t")
            }

        })




        ///이건 스트링으로 찍어본거 테스트로
//        val call : Call<String> = retrofitService.naverNewsApi("플로깅",40,"sim" )
//        call.enqueue( object  : Callback<String>{
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                //검색 성공
//                Log.i("네이버 api 검색 결과" , response.body().toString())
//            }
//
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(context, "네트워크를 확인해주세요", Toast.LENGTH_SHORT).show()
//            }
//
//        })

    }
}