package model

import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class User {

    companion object{



        const val GOOGLE = 1
        const val KAKAOLOGIN = 2
        const val EMAIL = 3
        const val NON = -1

        const val RECYCLE = 1
        const val RESTROOM = 2
        const val CAUTION = 3

        // 앱 로그인을 위한 사용자 이메일 , 비밀번호
        val email:String = "존재하지 않는 사용자 입니다."

        // 일정 추가시 등록 될 데이터들.
        val glovalItemList : MutableList<ScheduleVO> = mutableListOf<ScheduleVO>()

        var loginType:Int = -1

        var titlBundel : String = ""
        var j:Borad = Borad(title = titlBundel)
        var isisis = false


    }
}