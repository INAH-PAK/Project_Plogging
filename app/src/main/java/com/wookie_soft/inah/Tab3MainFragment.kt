package com.wookie_soft.inah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.FragmentMainTab3Binding

class Tab3MainFragment:Fragment(){
lateinit var  binding: FragmentMainTab3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTab3Binding.inflate(inflater, container, false)
        return binding.root
    }

}