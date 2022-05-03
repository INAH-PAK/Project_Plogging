package com.wookie_soft.inah

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.preference.*
// 환경설정 화면
class Tab4MainSettingFragment: PreferenceFragmentCompat() {
    //
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
    }

    val pref: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(
            requireContext()
        )
    }
    // 리스너 따로 정의
    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    // p0 : SharedPreferences , p1: 변경된 항목의 key 속성 값.
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
        val buffer = StringBuffer()
        when (p1) {
            "message", "vibration" -> buffer.append(p1 + " : " + pref.getBoolean(p1, false))
            "userName" -> {
                val s = pref.getString(p1, "")
                buffer.append("$p1 : $s")

                // 이 바뀐값도 summary에 값 써줘야 할 거 아냐?
                // 해당 설정값 객체 찾아오기  -> 다른 액티비티에서도 다 불러오기 가능하겠당
                // 자, setting.xml 에서 EditTextPreference 인 닉네임을 설정해야 함.
                val etPref = findPreference<EditTextPreference>(p1)
                etPref?.summary = s
            }
            "sound" -> {
                val s1 = pref.getString(p1, "")
                buffer.append("$p1 : $s1")
                findPreference<ListPreference>(p1)?.summary = s1
            }
            "favor" -> {
                //setOf : 빈 set 하나 만들기
                //listOf :빈 set 하나 만들기
                //mapOf :빈 set 하나 만들기
                //mutableSetOf : 빈 set 하나 만들기
                val datas: MutableSet<String>? = pref.getStringSet(p1, mutableSetOf<String>())
                // for( s in datas!!) buffer.append(s)   -> 만약 null이면 에러날 수 있응께 밑에 ㄱㄱ
                datas?.forEach { buffer.append(it) }
                findPreference<MultiSelectListPreference>(p1)?.summary = buffer.toString()
            }
        }

        //설정에 있는 값이 바뀌면 버퍼에 추가해서 보여주는 알랏다이얼로그
        AlertDialog.Builder(requireContext()).setMessage(buffer.toString()).create().show()

    }
}
