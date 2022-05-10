package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    val binding by lazy { ActivityRecordBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    }
}