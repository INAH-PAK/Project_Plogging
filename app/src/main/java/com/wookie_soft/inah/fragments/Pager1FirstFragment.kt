package com.wookie_soft.inah.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wookie_soft.inah.databinding.FragmentFirstPager1Binding

class Pager1FirstFragment : Fragment(){

    lateinit var fragmentbinbing: FragmentFirstPager1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentbinbing = FragmentFirstPager1Binding.inflate( inflater , container , false )
        return fragmentbinbing.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireActivity())
             .load("https://banner2.cleanpng.com/20180626/rlk/kisspng-bar-chart-statistics-computer-icons-clip-art-business-statistics-5b32ed42569974.0469936915300641943547.jpg")
             .into(fragmentbinbing.iv01)
    }


}