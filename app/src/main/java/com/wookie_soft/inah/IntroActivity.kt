package com.wookie_soft.inah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.wookie_soft.inah.databinding.ActivityIntroBinding
import java.util.*

class IntroActivity : AppCompatActivity() {

    val binding:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    val ani:Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.anim_intro) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val keyHash:String = Util.
        String keyHash = Utility.getKeyHash(this);

        Log.i("KEY",keyHash);

        // 인트로 에니메이션 종료 후 intent 로 Login Activity 시작
            val animationListener = object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    val intent = Intent( this@IntroActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                                    }
            }

        binding.tv.startAnimation(ani)
        ani.setAnimationListener(animationListener)

        }// onCreate



    }