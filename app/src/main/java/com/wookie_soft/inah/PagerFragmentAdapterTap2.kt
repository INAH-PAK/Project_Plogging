package com.wookie_soft.inah

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerFragmentAdapterTap2(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    val fragments= listOf<Fragment>(Pager2FirstFragment(),Pager2SecondFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}