package com.wookie_soft.inah.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.databinding.ActivityRecordBinding
import com.wookie_soft.inah.fragments.Pager2FirstFragment
import com.wookie_soft.inah.model.Borad
import com.wookie_soft.inah.model.User

class RecordActivity : AppCompatActivity() {
    // 내 일정 추가하기 .
    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }
    val pref: SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(this)  }
    val boradItems = mutableListOf<Borad>()
    val user_email by lazy { pref.getString("userEmail", "non email")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        User.isisis = false
        binding.btnOk.setOnClickListener { clickOK() }
        binding.btnCencle.setOnClickListener { clickCancle() }


        var p =0
        p+=1
        pref.edit().putInt("count",p)

    }

    fun clickOK(){
        val item = Borad(user_email!!,binding.checkShare.text.toString(),"2022",binding.etTitle.text.toString(),binding.etMsg.text.toString(),binding.etLocation.text.toString(),"","","flase")
        boradItems.add(item)

        //fragment로 객체 넘기기 -> 포맷이 있는 객체인 Gson으로 객체를 보내기.
        var fragment2 = Pager2FirstFragment()
        var bundle = Bundle()
        bundle.putSerializable("bored",item)
        fragment2.arguments = bundle
        User.isisis = true
        finish()

    }
    fun clickCancle(){
        User.isisis = true
        finish()
    }

}