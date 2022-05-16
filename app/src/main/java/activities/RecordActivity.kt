package activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    // 내 일정 추가하기 .
    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }
    lateinit var pref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        pref  =  PreferenceManager.getDefaultSharedPreferences(this)

        val user_email = pref.getString("userEmail", "non email")






    }
}