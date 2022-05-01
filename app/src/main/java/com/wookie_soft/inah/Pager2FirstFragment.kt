package com.wookie_soft.inah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.FragmentFirstPager2Binding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding

class Pager2FirstFragment : Fragment(){

lateinit var binding:FragmentFirstPager2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstPager2Binding.inflate( inflater , container, false)
        return binding.root
    }
}