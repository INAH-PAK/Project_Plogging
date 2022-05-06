package com.wookie_soft.inah

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.wookie_soft.inah.databinding.ActivityNameBinding

// 로그인 화면
// 닉네임 받는 조건 2가지  -  (사용자의 ) 키보드 입력버튼 클릭
//                      -  ( 화면의 ) 입력 버튼 클릭

class NameActivity : AppCompatActivity() {

    val binding:ActivityNameBinding by lazy { ActivityNameBinding.inflate(layoutInflater)  }
    private lateinit var manager: InputMethodManager
    val ani: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.anim_shake) }
    lateinit var userName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        //1
        binding.btn.setOnClickListener {
            ispass()
        }

        binding.civProfile.setOnClickListener {
            val intent:Intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            activityResultLauncher.launch(intent)
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

    val activityResultLauncher:ActivityResultLauncher<Intent> = registerForActivityResult( ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback {
            if(it.resultCode != RESULT_CANCELED){

                val userProfileImage:Intent? = it.data
                var imageUri:Uri? = it.data?.data
                Glide.with(this).load(imageUri).into(binding.civProfile)

            }
        })

    override fun onStart() {

        manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        binding.et.requestFocus()
        manager.showSoftInput(binding.et,InputMethodManager.SHOW_FORCED)
        super.onStart()
    }

        fun ispass(){
            if( binding.et.text.length <= 6) {

                userName = binding.et.text.toString()

                // 유저에게 받은 닉네임 값을 SharedPreferences 로 저장.
                val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val sharedPreferences:SharedPreferences = getSharedPreferences("name", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("userName",userName).commit() // 처음 사용자가 지정한 이름 넣기
                editor.putBoolean("isFirstRun",false).commit() // 처음 실행이 아니라는 걸 저장해두기
                Toast.makeText(this, " 환영합니다 $userName", Toast.LENGTH_SHORT).show()
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






