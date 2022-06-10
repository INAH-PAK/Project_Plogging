package activities

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import com.kakao.util.maps.helper.Utility
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityMap1Binding
import model.Marker
import model.MyMap
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView

class Map1Activity : AppCompatActivity() {
    val binding: ActivityMap1Binding by lazy { ActivityMap1Binding.inflate(layoutInflater) }
    private val ACCESS_FINE_LOCATION = 10     // Request Code
    val fusedLocationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private lateinit var mapView: MapView
    private lateinit var pplocation: Location

    // private lateinit var currentLocation:Location

    val myLocationMarker = MapPOIItem()


//
//    val customMarker:MapPOIItem

    val markerList =  mutableListOf<Marker>()


    //타이머
    var initTime = 0L   // 뒤로가기 버튼을 누른 시간을 저장하는 속성
    var pauseTime = 0L  //멈춘 시각을 저장하는 속성

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //  툴바를 제목줄로 설정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // 위에서 만든 액션바를 불러온건데 널 일 수 있으니 ?.
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 홈버튼을 뒤로가기로 하게따따
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24) // 내가 만든 아이코능로 설정
        supportActionBar?.setTitle("Map")


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
//         mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lat2, lng2), true); <- ??

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
            binding.chronometer.stop()
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            MyMap.timeResult = pauseTime
            binding.btn02Start.isEnabled = true
            binding.btn03Stop.isEnabled = false
        }

        binding.btn01.setOnClickListener {

            binding.chronometer.stop()

            AlertDialog.Builder(this)
                .setTitle("Add Marker")
                .setIcon(R.drawable.ic_baseline_add_location_alt_24)
                .setView(R.layout.dialog_add_marker)
                .setPositiveButton("추가하기", DialogInterface.OnClickListener { dialogInterface, i ->
                    //addMarker()
                })
                .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->
                    Toast.makeText(this, "마커 추가 취소", Toast.LENGTH_SHORT).show()
                })
                .create().show()

        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if(it != null){
                addMarker(it)
            }
        }


    }//onCreatMethod

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

        //카카오 지도 맵뷰
        mapView = MapView(this)
        val mapViewContainer: ViewGroup = binding.mapview
        mapViewContainer.addView(mapView)

        mapView.setShowCurrentLocationMarker(true) // 사용자의 현재 위치에 디폴트 마커 찍힘.

    }

    // 마커 이벤트 두개가 필요. -> 1. 현재 내 위치 보여주는 마커 (동적) , 2. 다른 사람들이 등록한 정보를 보여줄 마커 (고정)

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location): Location {

        // 지도화면을 내 위치에 맞게 보여주기
        mapView.setMapCenterPoint( mapPointWithGeoCoord(location.latitude.toDouble(),location.longitude.toDouble() ) ,true )

        return location
    }

    // 마커를 등록해주는 기능 메소드
    private fun addMarker(location: Location){

        myLocationMarker.itemName = "Default Marker"
        myLocationMarker.tag = 0
        myLocationMarker.mapPoint = mapPointWithGeoCoord(location.latitude,location.longitude)
        myLocationMarker.markerType = MapPOIItem.MarkerType.RedPin // 기본으로 제공하는 BluePin 마커 모양.
        myLocationMarker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(myLocationMarker)


//
//        MyMap.marker.itemName = "Default Marker"
//        MyMap.marker.tag = 0
//        MyMap.marker.customImageResourceId = com.wookie_soft.inah.R.drawable.icons3
//        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
//        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
//        MyMap.marker.markerType = MapPOIItem.MarkerType.CustomImage
//
//        //MyMap.mapPoint = mapPointWithGeoCoord(location.latitude.toDouble(),location.longitude.toDouble()).also {  MyMap.marker.mapPoint = it }
//        // MyMap.marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
//        MyMap.marker.selectedMarkerType =
//        MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        mapView.addPOIItem(MyMap.marker)

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
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    fun removeLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {

            onLocationChanged(p0.locations[0] )


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