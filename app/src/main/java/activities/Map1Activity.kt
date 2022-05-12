package activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.location.*
import com.kakao.util.maps.helper.Utility
import com.wookie_soft.inah.databinding.ActivityMap1Binding
import model.MyMap
import model.MyMap.Companion.marker
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView

class Map1Activity : AppCompatActivity() {
    val binding: ActivityMap1Binding by lazy { ActivityMap1Binding.inflate(layoutInflater) }
    private val ACCESS_FINE_LOCATION = 10     // Request Code
    val fusedLocationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private lateinit var mapView: MapView
    private lateinit var pplocation: Location


    //타이머
    var initTime = 0L   // 뒤로가기 버튼을 누른 시간을 저장하는 속성
    var pauseTime = 0L  //멈춘 시각을 저장하는 속성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //키해시
        val keyHash: String = Utility.getKeyHash(this)
        Log.i("키해시", keyHash)



        // 내위치 받자 !------------------------------------------------------------------------------------------------------------
        // 1. 동적 퍼미션
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        var checkResult = checkSelfPermission(permissions[0])
        if( checkResult == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions , ACCESS_FINE_LOCATION )
        }else{
            requestLocationUpdate() // 5초마다 위치정보 받아오기 메소드
        }
        // mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat2, lng2), true);

        // 타이머 재생 -
        binding.btn02Start.setOnClickListener {
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()
            //버튼 표시 여부
            binding.btn02Start.isEnabled = false
            binding.btn03Stop.isEnabled = true
        }
        //타이머 멈춤
        binding.btn03Stop.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.btn02Start.isEnabled = true
            binding.btn03Stop.isEnabled = false
        }

        binding.btn01.setOnClickListener {
            // 마커 추가 등록 엑티비티 만들기... 인텐트 ...

            val intent = Intent(this, Map2Activity::class.java)
            intent.putExtra("lat",pplocation.latitude.toDouble())
            intent.putExtra("lng",pplocation.longitude.toDouble())
            startActivity(intent)


        }



    }//onCreatMethod

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

        //카카오 지도 맵뷰
        mapView = MapView(this)
        val mapViewContainer: ViewGroup = binding.mapview
        mapViewContainer.addView(mapView)
    }


    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location): Location {

        mapView.setMapCenterPoint( mapPointWithGeoCoord(location.latitude.toDouble(),location.longitude.toDouble() ) ,true )

        MyMap.marker.itemName = "Default Marker"
        MyMap.marker.tag = 0
        MyMap.marker.customImageResourceId = com.wookie_soft.inah.R.drawable.icon_marker2
        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
        MyMap.marker.markerType = MapPOIItem.MarkerType.CustomImage

        MyMap.mapPoint = mapPointWithGeoCoord(location.latitude.toDouble(),location.longitude.toDouble()).also {  MyMap.marker.mapPoint = it }
       // MyMap.marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
        MyMap.marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(MyMap.marker)

        pplocation = location

        return location
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdate(){

        // 2. 위치정보 제공자 객체 얻어오기
        //Fused API : Google 지도에 사용되고 있는 위치정보 제공자 최적화 라이브러리
        //Google Fused API 라이브러리를 추가 : play-services-location


        //위치 정보 요청 객체 생성 및 설정
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도 우선시..[gps]
        locationRequest.interval = 1000 //5000ms[5초]간격으로 갱신
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());

    }

    fun removeLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {

            val lat = p0.lastLocation.latitude //위
            val lng = p0.lastLocation.longitude
            onLocationChanged(p0.locations[0] )


            Toast.makeText(
                this@Map1Activity,
                "GPS Location changed, Latitude: $lat, Longitude: $lng",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("Test", "GPS Location changed, Latitude: $lat, Longitude: $lng")

            val result = floatArrayOf()
            //Location.distanceBetween(lat, lng, 37.561235, 127.038207, result);

            //result[0]에 두 좌표사이의 m 거리가 계산되어 저장되어 있음.
            //if(result[0]<50){ //두 좌표사이의 거리가 50m 이내인가?

        }
    }



    // 동적퍼미션 콜백 메소드 - 위치정보사용에 대한 결과 알려줌
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                requestLocationUpdate()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다. \n 이 앱을 사용하실 수 없습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }// 동적퍼미션






}