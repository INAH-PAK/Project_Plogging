package com.wookie_soft.inah

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.FragmentSecondPager2Binding

class Pager2SecondFragment :Fragment(){

    lateinit var binding:FragmentSecondPager2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondPager2Binding.inflate(inflater , container, false)
        return  binding.root
    }
}