package com.wookie_soft.inah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.wookie_soft.inah.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val binding:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btn.setOnClickListener {
            val intent : Intent = Intent( this@IntroActivity , LoginActivity::class.java)
            startActivity(intent)
        }

    }


}