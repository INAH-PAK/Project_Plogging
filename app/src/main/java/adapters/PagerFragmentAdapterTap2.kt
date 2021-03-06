package adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import fragments.Pager2FirstFragment
import fragments.Pager2SecondFragment

class PagerFragmentAdapterTap2(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    val fragments= listOf<Fragment>(Pager2FirstFragment(), Pager2SecondFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}