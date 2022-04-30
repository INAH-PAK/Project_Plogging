package com.wookie_soft.inah

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.color.MaterialColors.getColor
import com.wookie_soft.inah.databinding.ActivityLoginBinding
import java.util.*
import javax.net.ssl.KeyManager

// 로그인 화면
// 닉네임 받는 조건 2가지  -  (사용자의 ) 키보드 입력버튼 클릭
//                      -  ( 화면의 ) 입력 버튼 클릭

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater)  }
    private lateinit var manager: InputMethodManager
    var userName:String = "손님"
    val ani: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.anim_shake) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        //1
        binding.btn.setOnClickListener {
            ispass()
        }

        //2
        // 키보드의 입력버튼을 누르면 넘어가기
        binding.et.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    // 사용자가 키보드의 입력버튼을 눌렀을 때
                    ispass()
                    return@setOnEditorActionListener true
                    }
                else -> false
                }
            }


        }//et

    override fun onStart() {

        manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.et.requestFocus()
        manager.showSoftInput(binding.et,InputMethodManager.SHOW_FORCED)
        super.onStart()
    }

        fun ispass(){
            if( binding.et.text.length <= 6) {
                userName = binding.et.text.toString()
                val intent: Intent = Intent(this, MainActivity::class.java)
                intent.putExtra("UserName",userName)
                startActivity(intent)

            }else {
                // 힌트 텍스트 에니메이션 넣기
                binding.tv3.setTextColor(this.getColor( R.color.red))
                binding.tv3.startAnimation(ani)
            }
        }



    }//onC






