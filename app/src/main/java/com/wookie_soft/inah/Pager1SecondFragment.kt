package com.wookie_soft.inah

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.FragmentFirstPager2Binding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
import com.wookie_soft.inah.databinding.FragmentSecondPager2Binding

class Pager1SecondFragment : Fragment() {
    lateinit var fragmentBinding:FragmentSecondPager1Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<ItemTab1>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentSecondPager1Binding.inflate( inflater , container , false )
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!

        // 일단 테스트 목적으로 아이템 만들어두자.
        items.add(ItemTab1("D-1","2022년 4월 26일", "오후 6시"," 길동이랑 만나기"))
        items.add(ItemTab1("D-3","2022년 4월 28일","오후66시"," 춘향이랑 아침산책"))
        items.add(ItemTab1("D-5","2022년 4월 30일", "오후8시"," 가족들과 저녁후 산책"))

        recyclerView = fragmentBinding.recyclerTab1
        fragmentBinding.recyclerTab1.adapter = childFragmentManager?.let{ RecyclerAdaopterTab1(activity as Context, items, it)}
    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

    }

}