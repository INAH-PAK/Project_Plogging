package com.wookie_soft.inah.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.ActivityMainBinding
import com.wookie_soft.inah.fragments.SettingsFragment
import com.wookie_soft.inah.fragments.Tab1MainFragment
import com.wookie_soft.inah.fragments.Tab2MainFragment
import com.wookie_soft.inah.fragments.Tab3MainFragment

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    //위치 퍼미션
    private val ACCESS_FINE_LOCATION = 10     // Request Code
    val fusedLocationProviderClient: FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    val fragments:MutableList<Fragment> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

            // 내위치 받자 !------------------------------------------------------------------------------------------------------------
            // 1. 동적 퍼미션
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
            var checkResult = checkSelfPermission(permissions[0])
            if( checkResult == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissions , ACCESS_FINE_LOCATION )
            }else{
               //위치 퍼미션 받기 완료.
            }

            //SAM 변환시는 return 이라는 단어를 아예 안써야 리턴이 됨.
            true

        }

        binding.floatingButton.setOnClickListener {
            val intent: Intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }



    }//on






}



