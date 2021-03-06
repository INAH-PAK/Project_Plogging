package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.wookie_soft.inah.databinding.FragmentMainTab2Binding
import adapters.PagerFragmentAdapterTap2
import com.wookie_soft.inah.R

class Tab2MainFragment:Fragment() {
    lateinit var binding:FragmentMainTab2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTab2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tap:TabLayout = binding.tab
        val viewPager :ViewPager2 = binding.pager
        val pagerAdaptor = PagerFragmentAdapterTap2(requireActivity())

        viewPager.adapter = pagerAdaptor
        TabLayoutMediator(tap,viewPager){tab,position->
            when (position) {
                0 ->  tab.text = " 내 일지 "
                1 ->  tab.text = "커뮤니티"
                }
        }.attach()

    }


}