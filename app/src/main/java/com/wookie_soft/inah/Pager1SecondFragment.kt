package com.wookie_soft.inah

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.FragmentFirstPager1Binding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding

class Pager1SecondFragment : Fragment() {

    val items : ArrayList<ItemTab1> = ArrayList()
    lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        items.apply {
            add(ItemTab1("D-2","2022년 4월 5일","오후 8"," 길동이랑 만나기"))
        }
        items.apply {
            add(ItemTab1("D-2","2022년 4월 5일","오후 8"," 길동이랑 만나기"))
        }
        val binbing: FragmentSecondPager1Binding = FragmentSecondPager1Binding.inflate( inflater , container , false )
        binbing.recyclerTab1.adapter = childFragmentManager?.let{ RecyclerAdaopterTab1(activity as Context, items, it)}

        return binbing.root
    }

}