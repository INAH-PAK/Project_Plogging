package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wookie_soft.inah.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    val binding: ActivityInfoBinding by lazy { ActivityInfoBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 리사이클러뷰의 기록 클릭시 나오는 상세보기 화면


    }
}