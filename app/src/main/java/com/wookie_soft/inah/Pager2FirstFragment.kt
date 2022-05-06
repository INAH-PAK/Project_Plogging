package com.wookie_soft.inah

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.FragmentFirstPager2Binding

class Pager2FirstFragment : Fragment() {
    lateinit var fragmentBinding:FragmentFirstPager2Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<ItemTab2First>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentFirstPager2Binding.inflate( inflater , container , false )
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!

        // 일단 테스트 목적으로 아이템 만들어두자.
        items.add(ItemTab2First("D+1","2022년 4월 26일",R.drawable.siba))
        items.add(ItemTab2First("D+3","2022년 4월 28일",R.drawable.siba ))
        items.add(ItemTab2First("D+5","2022년 4월 30일", R.drawable.siba))

        recyclerView = fragmentBinding.recycler
        fragmentBinding.recycler.adapter = childFragmentManager?.let{ RecyclerAdaopterTab2First(activity as Context, items , it)}
    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recycler.adapter?.notifyDataSetChanged()

    }

}