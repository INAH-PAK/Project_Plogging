package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityNewsUrlBinding

class NewsUrlActivity : AppCompatActivity() {
    val binding:ActivityNewsUrlBinding by lazy { ActivityNewsUrlBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.wv.webViewClient = WebViewClient() // 실제 웹 뷰에는 클라이언트가 없어서, 지정해주기.
        binding.wv.webChromeClient = WebChromeClient() // 없을 수 도 잇으니 크롭도 지정
        binding.wv.settings.javaScriptEnabled = true

        val newsUrl:String = intent.getStringExtra("newsUrl") ?: ""
        binding.wv.loadUrl(newsUrl)



    }
    override fun onBackPressed() {
        if (binding.wv.canGoBack()) binding.wv.goBack()
        else super.onBackPressed()
    }
}