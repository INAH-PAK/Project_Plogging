package com.wookie_soft.inah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val fragments:MutableList<Fragment> by lazy { mutableListOf() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userName = intent.getStringExtra("UserName")
        binding.tv01.setText(" 반가워요 $userName !")

        fragments.add(Tab1MainFragment()) //0
        fragments.add(Tab2MainFragment()) //1
        fragments.add(Tab3MainFragment()) //2

        supportFragmentManager.beginTransaction().add(R.id.container, fragments[0]).commit()

        binding.bnv.background = null





        binding.bnv.setOnItemSelectedListener {

            supportFragmentManager.fragments.forEach{
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
            val tran = supportFragmentManager.beginTransaction()

            when( it.itemId){
                R.id.bnv_menu_tab1 ->{
                    //   supportFragmentManager.beginTransaction().show(fragments[0]).commit()
                    tran.show(fragments[0])
                }
                R.id.bnv_menu_tab2 ->{
                    if( !supportFragmentManager.fragments.contains(fragments[1]))
                        tran.add(R.id.container,fragments[1])
                    tran.show(fragments[1])
                }
                R.id.bnv_menu_tab3 ->{
                    if( !supportFragmentManager.fragments.contains(fragments[2]))
                        tran.add(R.id.container,fragments[2])
                    tran.show(fragments[2])
                }

            }
            tran.commit()

            //SAM 변환시는 return 이라는 단어를 아예 안써야 리턴이 됨.
            true

        }//on

    }



}



