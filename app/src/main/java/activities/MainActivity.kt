package activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityMainBinding
import fragments.*

class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments:MutableList<Fragment> by lazy { mutableListOf() }



    // 퍼미션
    private val ACCESS_FINE_LOCATION = 10     // Request Code


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 동적으로 사용자에게 퍼미션 받기.
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        var checkResult = checkSelfPermission(permissions[0])
        if( checkResult == PackageManager.PERMISSION_DENIED){
            requestPermissions(permissions , ACCESS_FINE_LOCATION )
        }else{
           Log.i("사용자 위치권한", " 사용자 위치 권한 okok")
        }

        val userName = intent.getStringExtra("UserName")
       // binding.tv01.setText(" 반가워요 $userName !")

        // 총 4개의 탭
        fragments.add(Tab1MainFragment()) //0 - 홈
        fragments.add(Tab2MainFragment()) //1 - 내 일정 , 게시판
        fragments.add(Tab3MainFragment()) //2 - 내 텃밭
        fragments.add(SettingsFragment()) //3   - 설정

        // 프레그먼트 동적으로 추가.
        supportFragmentManager.beginTransaction().add(R.id.container, fragments[0]).commit()

        binding.bnv.background = null


        binding.bnv.setOnItemSelectedListener {

            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
            val tran = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.bnv_menu_tab1 -> {

                    //   supportFragmentManager.beginTransaction().show(fragments[0]).commit()
                    tran.show(fragments[0])
                }
                R.id.bnv_menu_tab2 -> {
                    if (!supportFragmentManager.fragments.contains(fragments[1]))
                        tran.add(R.id.container, fragments[1])
                    tran.show(fragments[1])
                }
                R.id.bnv_menu_tab3 -> {
                    if (!supportFragmentManager.fragments.contains(fragments[2]))
                        tran.add(R.id.container, fragments[2])
                    tran.show(fragments[2])
                }
                R.id.bnv_menu_tab4 -> {
                    if (!supportFragmentManager.fragments.contains(fragments[3]))
                        tran.add(R.id.container, fragments[3])
                    tran.show(fragments[3])
                }

            }
            tran.commit()

            //SAM 변환시는 return 이라는 단어를 아예 안써야 리턴이 됨.
            true

        }

        binding.floatingButton.setOnClickListener {
            val intent: Intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }



    }//on


    // 동적퍼미션 콜백 메소드 - 위치정보사용에 대한 결과 알려줌
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
//                requestUserLocation()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(this, "위치 권한이 거절되었습니다. \n 이 앱을 사용하실 수 없습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }// 동적퍼미션







}



