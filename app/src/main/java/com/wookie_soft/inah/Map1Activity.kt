package com.wookie_soft.inah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wookie_soft.inah.databinding.ActivityMap1Binding

class Map1Activity : AppCompatActivity() {

    val binding:ActivityMap1Binding by lazy { ActivityMap1Binding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var name = "왜 안올라가"
        binding.btn.setOnClickListener {
            val intent = Intent( this@Map1Activity , Map2Activity::class.java)
            startActivity(intent)
        }




    }
}