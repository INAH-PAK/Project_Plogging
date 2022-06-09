package activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityMap2Binding
import model.MyMap
import model.MyMap.Companion.marker
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.n.api.internal.NativeMapLocationManager.isShowingCurrentLocationMarker
import net.daum.mf.map.n.api.internal.NativeMapLocationManager.setShowCurrentLocationMarker

class Map2Activity : AppCompatActivity() {
    val binding:ActivityMap2Binding by lazy { ActivityMap2Binding.inflate(layoutInflater) }
    lateinit var mapView: MapView
    val pref: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

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

        // 마커추가

        mapView.addPOIItem(MyMap.marker)

       Log.i(" 마커 보여지는 중?",isShowingCurrentLocationMarker().toString() )

        binding.chip01.setOnClickListener {
            MyMap.marker.customImageResourceId =R.drawable.icons4
            MyMap.marker.customSelectedImageResourceId =R.drawable.icons4
            mapView.addPOIItem(MyMap.marker)
            Log.i("마커", marker.itemName)
        }
        binding.chip02.setOnClickListener {
            MyMap.marker.customImageResourceId =R.drawable.icons22
            MyMap.marker.customSelectedImageResourceId =R.drawable.icons22
            mapView.addPOIItem(MyMap.marker)}
        binding.chip02.setOnClickListener {
            MyMap.marker.customImageResourceId =R.drawable.icons5
            MyMap.marker.customSelectedImageResourceId =R.drawable.icons5
            mapView.addPOIItem(MyMap.marker)}

        val mapPoint = MapPoint.mapPointWithGeoCoord( lat,lng ).also { marker.mapPoint = it }





        binding.btn03Stop.setOnClickListener {
            // 서버로 마커 전달.



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