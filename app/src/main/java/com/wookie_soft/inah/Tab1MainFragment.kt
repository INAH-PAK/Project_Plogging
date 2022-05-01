package com.wookie_soft.inah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wookie_soft.inah.databinding.FragmentMainTab1Binding

class Tab1MainFragment:Fragment(){


    lateinit var binding:FragmentMainTab1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTab1Binding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tap: TabLayout = binding.tab
        val viewPager:ViewPager2 = binding.pager
        val pagerAdapter = PagerFragmentAdapterTap1(requireActivity())

        //페이저 안에 들어갈 fragment 2개 추가
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(tap,viewPager){tab,position->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

    }




}



