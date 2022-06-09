package Network

import model.ScheduleVO
import model.UserAccount
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    //TODO 회원가입시 저장될 사용자 정보 ? 각 토큰값들. . . 좀 정리하자. 머리가 너무 안돌아가 ㅜㅜ 자고싶ㄷ
    @POST("insertUserAccountToDB.php")
    fun insertUserInfoToDB(@Body userAccount:UserAccount):Call<String>

    // DB의 회원정보를 Load 하는 인터페이스 메소드. . .
    @POST("loadUserAccountFromDB.php")
    fun loadUserAccountFromDB():Call<UserAccount>


    // 테스트 용으로 ..
    @POST("pppDB.php")
    fun postMethodTest(@Body itemModel:ScheduleVO): Call<String>



}