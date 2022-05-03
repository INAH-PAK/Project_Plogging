package com.wookie_soft.inah

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class StartApplicationForKakao: Application() {
    override fun onCreate() {
        super.onCreate()

        //Kakao SDK initialize
        // 카카오 디벨로퍼 -> 해당 프로젝트 -> 앱 키 -> 네이티브 앱 키
        KakaoSdk.init(this, "eca63ad5e210f83cbd0ecb7118d1f15b")


    }
}