package com.wookie_soft.inah.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityFinalLoginBinding
import com.wookie_soft.inah.model.MySharedPreferenceManager
import com.wookie_soft.inah.model.User
import java.util.*

class FinalLoginActivity : AppCompatActivity() {

    //파이어베이스 인증관리객체
    val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    val binging: ActivityFinalLoginBinding by lazy {
        ActivityFinalLoginBinding.inflate(
            layoutInflater
        )
    }

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity

    companion object {

        // 로그아웃 기능 ->  어떤식으로 로그아웃 시킬지 생각하기.
        private fun clickUnlink(){
            val firebaseAuth = FirebaseAuth.getInstance()
            when(User.loginType) {

                User.GOOGLE, User.EMAIL -> firebaseAuth.signOut()
                User.KAKAOLOGIN -> {
                    UserApiClient.instance.unlink {
                        if(it != null){
                            Log.i("로그아웃 상태", "faile")
                        }else{
                            Log.i("로그아웃 상태", "success")
                        }
                    }
                }

            }

        }//로그아웃

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binging.root)

        //이메일 로그인
        binging.emailLogin.setOnClickListener { clickSignInEmail() }
        //이메일 회원가입
        binging.SignUpEmail.setOnLongClickListener { clickSignUpEmail() }

        // 카카오
        binging.kakaoLogin.setOnClickListener { clickKakaoLogin() }

        //구글
        binging.googleLogin.setOnClickListener { clickGoogleLogin() }

        binging.tv01.setOnClickListener { nextActivity() }
    }// onCreat

    private fun clickSignInEmail() {
        //이메일 로그인
        val email = binging.et01.text.toString()
        val pw = binging.et02.text.toString()
        firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener {
            if (it.isSuccessful) {
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    Toast.makeText(this, "이메일 로그인 성공", Toast.LENGTH_SHORT).show()
                    val mail: String = firebaseAuth.currentUser!!.email.toString()
                    Log.i("사용자 이메일", mail)
                    User.loginType = User.EMAIL
                    MySharedPreferenceManager.userEmail = email
                    nextActivity()
                } else {
                    Toast.makeText(this, "이메일과 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                MySharedPreferenceManager.userEmail = "non"
            }
        }
    }

    private fun clickSignUpEmail(): Boolean {
        // 이메일 회원가입
        //이메일 및 비밀번호 인증 방식의 회원가입 - 입력된 이메일로 [인증확인]메일이 보내지고 사용자가 확인했을때 가입이 완료되는 방식
        val email: String = binging.et01.text.toString()
        val pw: String = binging.et02.text.toString()

        Log.i("이메일 쓴거", email + "")

        firebaseAuth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
            //입력된 이메일과 패스워드가 사용가능한지..유효성 검사 결과
            //1. 이메일 형식에 맞는가? XX@XX.XX
            //2. 패스워드가 6자리 이상인가?
            //3. 기존 이메일에 같은 이름이 있는지 확인
            if (task.isSuccessful) {
                Toast.makeText(this, "입력된 이메일로 인증해주세요", Toast.LENGTH_SHORT).show()
                //현재 상태도 firebase에는 회원등록된 상태임. 다만, 인증이 안되어 있음.

                //입력된 이메일로 [인증확인] 메일 전송 및 전송성공여부 확인
                Objects.requireNonNull(firebaseAuth.currentUser)!!.sendEmailVerification()
                    .addOnCompleteListener {
                        if (task.isSuccessful) Toast.makeText(
                            this,
                            "전송된 이메일을 확인하시고 인증하세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                        else Toast.makeText(this, "메일 전송에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "이메일과 비밀번호형식을 다시 확인해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
        nextActivity()
        return true
    }

    private fun clickGoogleLogin(){

        // 구글 로그인 액티비티를 실행하는 Intent 객체 얻어오기
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("774564097815-sd83s0v674vg7jilc8nj6ffip52gm2pb.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val intent = GoogleSignIn.getClient(this, signInOptions).signInIntent
        // 로그인 결과를 가려올 인텐트가 필요한데, 콜백기능을 가져야 하기째문에 엑티비티 리절트 런처 필요.
        resultLauncher.launch(intent) //resultLauncher 맴버변수로 밑에 정의.
    }

    val resultLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> {

            //로그인 결과를 가져온 인텐츠 객체 소환
            val intent: Intent? = it.data

            //Intent로 부터 구글 계정 정보를 가져오는 작업 객체 생성
            var task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            // 구글 계정 가져와서 텍스트로 찍어보기. -> SharedPreference 로 저장 해야 함
            var account:GoogleSignInAccount = task.result
            var email:String = account.email.toString()
            MySharedPreferenceManager.userEmail = email
            Log.i(" 구글 이메일은 :" , email)
            User.loginType = User.GOOGLE
            nextActivity()

        })

    private fun clickKakaoLogin() {
        // 카카오 로그인

        val callback2: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            Log.i("카카오 로그인 에러", "카카오 로그인 에러")
        }


        //      https://developers.kakao.com/console/app/739315/product/login
        //          5       .  꼭 카카오 프로젝트 페이지에서 카카로 로그인 활성화 시키기 !!!!!
        // 카카오 로그인 동의항목도 설정하기 ! https://developers.kakao.com/console/app/739315/product/login/scope

        // 카카오 로그인이 가능한지 물어보기
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            // 카톡 로그인(카카오의 권장)   --> loginWithKakaoTalk 의 두 번째 파라미터는 맨 위에 만들었음.

            UserApiClient.instance.loginWithKakaoTalk(this, callback = { token, error ->
                if (error != null) { //에러가 있으면 망한고잖아? ㅋㅋ 에러 띄우자. 근데 카카오 디벨로퍼 사이트에선 여기서 카카오 계정 로그인으로 하라고 함. 근데 귀찮으니까 일단 이걸로.
                    Toast.makeText(this, "카카오 로그인 실패 : ${error}", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "카카오 로그인 성공 : ${error}", Toast.LENGTH_SHORT).show()
                    User.loginType = User.KAKAOLOGIN
                    nextActivity()
                }
                // 어차피 카카오 로그인은 콜백메소드 무조건 실행하니, 여기서 정보를 가져오자.
                loadKakaoUserInfo()
            })

        } else {
            // 카카오 계정 로그인
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback2)
        }
    }



    // 카카오 로그인 성공 시 데이터를 가져오는 메소드
    @SuppressLint("CommitPrefEdits")
    private fun loadKakaoUserInfo(){
        UserApiClient.instance.me { user, error ->
            if(error != null) {
                Toast.makeText(this, "사용자 정보 요청 실패 ", Toast.LENGTH_SHORT).show()
                MySharedPreferenceManager.userEmail = "non"
            }
            else if ( user != null ){
                // 어떤 정보를 받아올 지 ~~ 이메일만 가져오자.
                val email = user.kakaoAccount?.email
                MySharedPreferenceManager.userEmail = "email"
                Log.i("카카오 이메일", email.toString())
            }
        }
    }

    private fun nextActivity(){
        val intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
    }



    }//class





