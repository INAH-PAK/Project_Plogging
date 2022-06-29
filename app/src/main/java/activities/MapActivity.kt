package activities

import Network.RetrofitHelper
import Network.RetrofitService
import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.location.*
import com.kakao.util.maps.helper.Utility
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityMapBinding
import model.Marker
import model.MyMap
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapActivity : AppCompatActivity() {


    val binding: ActivityMapBinding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    val retrofit = RetrofitHelper.getRetrofitInstans()
    val retrofitService = retrofit.create(RetrofitService::class.java)
    val mapView:MapView by lazy { MapView(this) }

    // 구글 FusedLocation
    var userLocation: Location? = null
    val fusedLocationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    lateinit var userEmail:String


    // 핸드폰 내부 저장소
    private val sharedPreferences:SharedPreferences by lazy {  PreferenceManager.getDefaultSharedPreferences(this)}
    private val prefEditor: SharedPreferences.Editor by lazy {  sharedPreferences.edit()}

    val marker = MapPOIItem()
    var lat:String = "37.5666805"
    var lng:String = "126.9784147"

    val markerList =  mutableListOf<Marker>()
    //타이머
    var initTime = 0L   // 뒤로가기 버튼을 누른 시간을 저장하는 속성
    var pauseTime = 0L  //멈춘 시각을 저장하는 속성


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 서버DB에 있던 마커들 불러와서 지도에 찍기
        loadDBFromMarker()



        setMapAndMarker()

        userEmail = sharedPreferences.getString("userEmail","식별할 수 없는 사용자 입니다.").toString()

        //키해시
        val keyHash: String = Utility.getKeyHash(this)
        Log.i("키해시", keyHash)

        //  툴바를 제목줄로 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 위에서 만든 액션바를 불러온건데 널 일 수 있으니 ?.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 홈버튼을 뒤로가기로 하게따따
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24) // 내가 만든 아이코능로 설정
        supportActionBar?.setTitle("Map")
        binding.mapview.addView(mapView)



        // 타이머 재생 -
        binding.btn02Start.setOnClickListener { startTimer() }
        //타이머 멈춤
        binding.btn03Stop.setOnClickListener { stopTimer() }

        // 마커 추가 버튼 클릭 이벤트 ->  1. 사용자가 정보 입력시, 레트로핏 작업을 통헤 서버 DB로 저장.
        binding.btn01.setOnClickListener { clickBtnAddMarker() }



    }//onCreatMethod

    private fun setMapAndMarker(){ // 맨 처음 지도와 마커를 설정하는 메소드

        // 마카 or 말풍선의 클릭이벤트에 반응하는 리스너 등록
        // ** 반드시 마커 추가하는 것보다 먼저 등록되어 있어야 동작함. **
        mapView.setPOIItemEventListener(markerEventListener)

        lat = (userLocation?.latitude ?: "37.5666805").toString()
        lng = (userLocation?.longitude ?: "126.9784147").toString()

       var myMapPoint:MapPoint = MapPoint.mapPointWithGeoCoord(lat.toDouble(), lng.toDouble())
        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 5, true)
        mapView.zoomIn(true)
        mapView.zoomOut(true)

        // 내 위치 마커
        marker.apply {
            itemName="ME"
            mapPoint= myMapPoint
            mapPoint = MapPoint.mapPointWithGeoCoord(lat.toDouble(), lng.toDouble())
            markerType= MapPOIItem.MarkerType.BluePin
            selectedMarkerType= MapPOIItem.MarkerType.RedPin
        }
        mapView.addPOIItem(marker)

        loadDBFromMarker() // 서버에 저장된 마커들을 찍기
    }




    private fun stopTimer(){
        binding.chronometer.stop()
        pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
        MyMap.timeResult = pauseTime
        binding.btn02Start.isEnabled = true
        binding.btn03Stop.isEnabled = false
    }

    private fun startTimer(){
        binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
        binding.chronometer.start()
        //버튼 표시 여부
        binding.btn02Start.isEnabled = false
        binding.btn03Stop.isEnabled = true
    }

    private fun clickBtnAddMarker(){

        binding.chronometer.stop()
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_marker, null)
        val et:EditText = dialogView.findViewById(R.id.dialog_edit)
        AlertDialog.Builder(this)
            .setTitle("Add Marker")
            .setIcon(R.drawable.ic_baseline_add_location_alt_24)
            .setView(dialogView)
            .setPositiveButton("추가하기", DialogInterface.OnClickListener { dialogInterface, i ->
               val markerInstance: Marker = Marker(userEmail , lat,lng, G.userAccount!!.loginType, et.text.toString() )
               insertDBToMakers(markerInstance)
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->
                Toast.makeText(this, "마커 추가 취소", Toast.LENGTH_SHORT).show()
            })
            .create().show()
    }

    // 서버 DB에 저장된 마커들을 불러오는 메소드
    private fun loadDBFromMarker(){
        val mMarker = ArrayList<Marker>()
        // 여기서는 retrofit으로 서버 DB에 저장 된 마커들을 불러와서 찍기.
        val call:Call<ArrayList<Marker>> = retrofitService.loadDBMarkers()
        call.enqueue(object : Callback<ArrayList<Marker>>{
            override fun onResponse(
                call: Call<ArrayList<Marker>>,
                response: Response<ArrayList<Marker>>
            ) {
             Log.i(" 성공", response.body().toString())
                val mmMarker: ArrayList<Marker>? = response.body()
                if (mmMarker == null) {
                    Toast.makeText(this@MapActivity, " 서버 DB에 저장된 값이 없습니다.", Toast.LENGTH_SHORT).show()
                }else {
                    Log.i("ttttt", "실행 됨.")
                    mmMarker.forEach {
                        val point: MapPoint = mapPointWithGeoCoord(it.latitude.toDouble(),it.longitude.toDouble())
                        marker.apply {
                            Log.i("ttttt2222", "실행 됨2222.")
                            itemName= it.message
                            mapPoint= point
                            markerType= MapPOIItem.MarkerType.RedPin
                            selectedMarkerType= MapPOIItem.MarkerType.YellowPin
                            // 해당 POI item(마커)와 관련된 정보를 저장하고 있는 데이터객체를 보관
                            userObject= it
                        }
                        mapView.addPOIItem(marker)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Marker>>, t: Throwable) {
                Log.i("시ㄹ패","실패패패패패   ===   $t")
            }

        })
    }







    // 마커 추가시 서버로 정보 보내는 메소드
    private fun insertDBToMakers(marker:Marker){
        //서버로 보낼 값을 가진 call 바로 보내버리기
        val call: Call<String> = retrofitService.insertDBMarkers(marker)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("서버 응답 성공시", response.body() + "")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("서버 응답 실패", t.message + "")
            }
        })

    }

    private fun requestUserLocation(){
        //내 위치 정보얻어오는 기능 코드

        val request: LocationRequest = LocationRequest.create()
        request.interval = 1000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        // 실시간 위치정보 갱신 요청

        // 퍼미션 검사
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient.requestLocationUpdates(request,locaionCallback, Looper.getMainLooper())
    }

    private val locaionCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            // 계속해서 내 위치 가져옴.
            userLocation = p0.lastLocation

        }
    }


    override fun onSupportNavigateUp(): Boolean { // 사용자가 업 버튼을 눌렀을 때
        onBackPressed() // 업버튼 눌렀을 때 뒤로가기 기능 을 실현하겠다.
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return  super.onOptionsItemSelected(item)
    }


    override fun onPause() {
        super.onPause()
        removeLocationUpdate()
        binding.mapview.removeAllViews()
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdate()
//        mapView.setShowCurrentLocationMarker(true) // 사용자의 현재 위치에 디폴트 마커 찍힘.

    }

    // 마커 이벤트 두개가 필요. -> 1. 현재 내 위치 보여주는 마커 (동적) , 2. 다른 사람들이 등록한 정보를 보여줄 마커 (고정)

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location): Location {

        // 지도화면을 내 위치에 맞게 보여주기
        mapView.setMapCenterPoint( mapPointWithGeoCoord(location.latitude.toDouble(),location.longitude.toDouble() ) ,true )
        marker.mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude,location.longitude)
        Toast.makeText(this@MapActivity, "현위치 ${location.latitude}", Toast.LENGTH_SHORT).show()
        return location
    }


    // 마커를 등록해주는 기능 메소드
//    private fun addMarker(){
//
//        // 마카 or 맘풍선의 클릭이벤트에 반응하는 리스너 등록
//        // ** 반드시 마커 추가하는 것보다 먼저 등록되어 있어야 동작함. **
//        mapView.setPOIItemEventListener(markerEventListener)
//
//
//        var lat: Double = userLocation?.latitude ?: 37.5666805
//        var lng: Double = userLocation?.longitude ?: 126.9784147
//
//        var myMapPoint: MapPoint = MapPoint.mapPointWithGeoCoord(lat, lng)
//        mapView.setMapCenterPointAndZoomLevel(myMapPoint, 5, true)
//        mapView.zoomIn(true)
//        mapView.zoomOut(true)
//
//
//        myLocationMarker.itemName = "Default Marker"
//        myLocationMarker.tag = 0
//        myLocationMarker.mapPoint = mapPointWithGeoCoord(location.latitude,location.longitude)
//        myLocationMarker.markerType = MapPOIItem.MarkerType.RedPin // 기본으로 제공하는 BluePin 마커 모양.
//        myLocationMarker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        mapView.addPOIItem(myLocationMarker)
//
//
//    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(){

        // 2. 위치정보 제공자 객체 얻어오기
        //Fused API : Google 지도에 사용되고 있는 위치정보 제공자 최적화 라이브러리
        //Google Fused API 라이브러리를 추가 : play-services-location

        //위치 정보 요청 객체 생성 및 설정
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도 우선시..[gps]
        locationRequest.interval = 1000 //5000ms[5초]간격으로 갱신
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(this, "ㅇㅇㅇㅇ", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this, "콜백함수 불러오기", Toast.LENGTH_SHORT).show()
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    fun removeLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            userLocation = p0.lastLocation
            onLocationChanged(p0.locations[0] )

        }
    }

//마커나 말풍선이 클릭되는 이벤트에 반응하는 리스너객체  ////////
private val markerEventListener:MapView.POIItemEventListener = object : MapView.POIItemEventListener{
    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        // 마커 클릭시 발동하는 메소드
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
        // deprecated .. 이제는 아래 오버로딩된 메소드 사용 권장
    }

    override fun onCalloutBalloonOfPOIItemTouched( // 마커 말풍선 클릭시 반응 객체
        p0: MapView?,
        p1: MapPOIItem?, // 마커객체
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
        // 마커의 말풍선을 클릭했을때 발동하는 메소드
        // 두번째 파라미터 p1 : 마커객체
        if( p1?.userObject == null ) return

//        val place: Place= p1?.userObject as Place
//
//        // 장소 상세정보 보여주는 화면으로 이동
//        // 웹 뷰를 띄워주는 ->  place activity 로 !!
//        val intent= Intent(context, PlaceUrlActivity::class.java)
//        intent.putExtra("place_url", place.place_url)
//        startActivity(intent)

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
        // 마커를 드래그하여 움직였을때 발동
    }

}// 마커 이벤트 리스너

}