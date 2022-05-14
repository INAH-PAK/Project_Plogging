package activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import com.wookie_soft.inah.databinding.ActivityMap2Binding
import model.MyMap.Companion.marker
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class Map2Activity : AppCompatActivity() {
    val binding:ActivityMap2Binding by lazy { ActivityMap2Binding.inflate(layoutInflater) }
    lateinit var mapView: MapView
    val lat: Double = 0.0
    val lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val secondIntent = intent
        intent.getDoubleExtra("lat",lat)
        intent.getDoubleExtra("lng",lng)

        mapView = MapView(this)
        val mapViewContainer: ViewGroup = binding.mapview
        mapViewContainer.addView(mapView)

        //마커 추가
//        marker.itemName = "Default Marker"
//        marker.tag = 0
        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
        //카카오맵은 참고로 new MapPoint()로  생성못함. 좌표기준이 여러개라 이렇게 메소드로 생성해야함
        val mapPoint = MapPoint.mapPointWithGeoCoord(
            lat.toDouble(),
            lng.toDouble()
        ).also { marker.mapPoint = it }
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
        marker.selectedMarkerType =
            MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mapView.addPOIItem(marker)


        binding.btn03Stop.setOnClickListener {
            var intent = Intent( this@Map2Activity, Map1Activity::class.java )
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        binding.mapview.removeAllViews()
    }

    override fun onResume() {
        super.onResume()


    }
}