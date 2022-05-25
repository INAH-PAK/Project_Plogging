package com.wookie_soft.inah.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wookie_soft.inah.fragments.Pager1FirstFragment
import com.wookie_soft.inah.fragments.Pager1SecondFragment

class PagerFragmentAdapterTap1(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    val fragments= listOf<Fragment>(Pager1FirstFragment(), Pager1SecondFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}