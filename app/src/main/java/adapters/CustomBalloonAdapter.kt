package adapters

import android.view.View
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem

// 마커 클릭시 나타낼 커스텀 말풀선
class CustomBalloonAdapter(customBalloon:View) : CalloutBalloonAdapter {



    override fun getCalloutBalloon(p0: MapPOIItem?): View { //

        TODO("Not yet implemented")
    }

    override fun getPressedCalloutBalloon(p0: MapPOIItem?): View {
        TODO("Not yet implemented")
    }
}