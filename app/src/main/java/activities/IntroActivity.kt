package activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    val binding: ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    val ani: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.anim_intro) }
    var isFirstRun:Boolean = true
    lateinit var userName:String
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(binding.root)
        pref  =  PreferenceManager.getDefaultSharedPreferences(this)
//        val keyHash = Utility.getKeyHash(this)//onCreate 안에 입력해주자
//        Log.d("키해쉬", keyHash)

            loadPreference()  // SharedPreference 값 읽어오기

            // 인트로 에니메이션 종료 후 -> 최초 로그인 여부 판단 후 -> 조건에 맞는  Activity 시작
            val animationListener = object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {

                    val intent = when(userName) {
                        "" -> {
                            Intent( this@IntroActivity, NameActivity::class.java)
                        }
                        else -> {
                            Toast.makeText(this@IntroActivity, "환영합니다  $userName", Toast.LENGTH_SHORT).show()
                            Intent(this@IntroActivity, MainActivity::class.java)
                        }
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(intent)
                }
            }

            binding.tv.startAnimation(ani)
            ani.setAnimationListener(animationListener)

        }// onCreate


        // 저장되어 있는 shered preference에 저장된 값들을 읽어오기 ( 사용자가 설정한 값들 ~~)
        private fun loadPreference(){
            isFirstRun = pref.getBoolean("isFirstRun",false)
            userName = pref.getString("userName","").toString()
            var isLogin:Boolean = pref.getBoolean(("login"),false)
            var isMessage:Boolean = pref.getBoolean("massege",false) // getBoolean( 식별자 , 디폴트값 )
            var isVibrate:Boolean = pref.getBoolean("vibration",false) // getBoolean( 식별자 , 디폴트값 )

        }



    }

