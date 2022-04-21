package com.wookie_soft.inah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.FragmentMainTab3Binding

class Tab3MainFragment:Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    val binding:FragmentMainTab3Binding by lazy { FragmentMainTab3Binding.inflate(layoutInflater) }
}