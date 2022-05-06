package com.wookie_soft.inah

import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerFragmentAdapterTap1(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    val fragments= listOf<Fragment>(Pager1FirstFragment(),Pager1SecondFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}