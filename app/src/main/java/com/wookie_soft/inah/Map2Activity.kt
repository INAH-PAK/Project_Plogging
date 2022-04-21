package com.wookie_soft.inah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wookie_soft.inah.databinding.ActivityLoginBinding
import com.wookie_soft.inah.databinding.ActivityMap2Binding

class Map2Activity : AppCompatActivity() {

    val binding:ActivityMap2Binding by lazy { ActivityMap2Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            val intent = Intent( this@Map2Activity , Map3Activity::class.java)
            startActivity(intent)
        }

    }

}