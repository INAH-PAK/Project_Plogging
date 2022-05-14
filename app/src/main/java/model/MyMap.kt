package model

import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MyMap {
    // 맵에 필요한 데이터들 정의
    companion object {

        val marker by lazy { MapPOIItem() }
        lateinit var mapPoint: MapPoint

    }
}



