package fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.R
import adapters.RecyclerAdaopterTab2Second
import com.wookie_soft.inah.databinding.FragmentSecondPager2Binding
import model.ItemTab2Second

class Pager2SecondFragment  : Fragment() {
    lateinit var fragmentBinding: FragmentSecondPager2Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<ItemTab2Second>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentSecondPager2Binding.inflate( inflater , container , false )
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!

        // 일단 테스트 목적으로 아이템 만들어두자.
        items.add(ItemTab2Second("사용자1", R.drawable.siba,"2022년 4월 26일","도산공원에서 행복한 하루"))
        items.add(ItemTab2Second("사용자2", R.drawable.siba,"2022년 4월 30일"," 상섬동 코엑스 근처 꿀팁"))
        items.add(ItemTab2Second("사용자3", R.drawable.siba,"2022년 4월 2ㅈ6일","재활용 쓰레기 이렇게 하시는거 아셨어요?"))

        recyclerView = fragmentBinding.recycler
        fragmentBinding.recycler.adapter = childFragmentManager?.let{ RecyclerAdaopterTab2Second(activity as Context, items , it) }
    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recycler.adapter?.notifyDataSetChanged()

    }

}