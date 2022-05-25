package com.wookie_soft.inah.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.*
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityFinalLoginBinding

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    // 요기 설정값들이 바뀔때마다 알려주는 lisenner 달기
    val pref: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }

    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener) // 여기서 이 안에 익명클래스로 만들 수 있는데, 그러면 이 리스너를 onPause 여기서 못쓰니까 밑에서 리스너 만들자.
    }

    override fun onPause() {
        super.onPause()
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }


//     코틀린 익명객체 만드는 법 !! new 해서 일일히 오버라이드 안해도 됨
//    val listener= object :SharedPreferences.OnSharedPreferenceChangeListener{
//    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
//    }

    // p0 : SharedPreferences , p1: 변경된 항목의 key 속성 값.


    val listener= SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
        val buffer = StringBuffer()
        when(p1){
            "message","vibration" -> buffer.append(p1 + " : " + pref.getBoolean(p1,false))
            "nickname" -> {
                val s = pref.getString(p1,"")
                buffer.append("$p1 : $s")

                // 이 바뀐값도 summary에 값 써줘야 할 거 아냐?
                // 해당 설정값 객체 찾아오기  -> 다른 액티비티에서도 다 불러오기 가능하겠당
                // 자, setting.xml 에서 EditTextPreference 인 닉네임을 설정해야 함.
                val etPref = findPreference<EditTextPreference>(p1)
                etPref?.summary = s
            }
            "logout_app" -> {
                val s = pref.getString(p1,"")
                buffer.append("$p1 의 $s 계정을 삭제합니다.")


            }

        }

        //설정에 있는 값이 바뀌면 버퍼에 추가해서 보여주는 알랏다이얼로그
        AlertDialog.Builder(requireContext()).setMessage(buffer.toString()).create().show()

    }
}







