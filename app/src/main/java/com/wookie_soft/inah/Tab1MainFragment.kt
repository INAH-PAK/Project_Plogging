package com.wookie_soft.inah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.FragmentMainTab1Binding

class Tab1MainFragment:Fragment(){

   

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    
    val binding: FragmentMainTab1Binding  by lazy { FragmentMainTab1Binding.inflate(layoutInflater) }
    
}