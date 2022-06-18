package Network

import model.Marker
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


    // 달력 DB로 일정 데이터 보내기
    @POST("pppDB.php")
    fun postMethodTest(@Body itemModel:ScheduleVO): Call<String>

    // 마커 DB로 마커추가 데이터 보내기
    @POST("insertDBMarkers.php")
    fun insertDBMarkers(@Body markerInfo:Marker): Call<String>

    // 달력 DB에서 일정 데이터 불러오기
    @POST("loadDBSchedule.php")
    fun loadDBScheduleVO():Call<ArrayList<ScheduleVO>>

    // 마커 DB에서 마커 데이터 불러오기
    @POST("loadDBMarker.php")
    fun loadDBMarkers():Call<ArrayList<Marker>>

//    @POST("loadDB.php")
//    fun loadDBMarkers():Call<String>


}