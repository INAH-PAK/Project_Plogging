package com.wookie_soft.inah

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wookie_soft.inah.databinding.FragmentFirstPager1Binding

class Pager1FirstFragment : Fragment(){

    lateinit var binbing: FragmentFirstPager1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binbing = FragmentFirstPager1Binding.inflate( inflater , container , false )
        return binbing.root
    }
}