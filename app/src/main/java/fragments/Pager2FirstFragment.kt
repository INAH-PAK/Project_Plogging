package fragments

import activities.RecordActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.R
import adapters.RecyclerAdaopterTab2First
import android.content.Intent
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wookie_soft.inah.databinding.CustomDialogBinding
import com.wookie_soft.inah.databinding.FragmentFirstPager2Binding
import model.ItemTab2First

class Pager2FirstFragment : Fragment() {
    lateinit var fragmentBinding:FragmentFirstPager2Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<ItemTab2First>()

    lateinit var pref: SharedPreferences


    val datePickerBuilder: DatePickerBuilder by lazy {
        DatePickerBuilder(requireContext(),listener ).setPickerType(
            CalendarView.ONE_DAY_PICKER)}
    val datePicker: DatePicker by lazy {  datePickerBuilder.build() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentFirstPager2Binding.inflate( inflater , container , false )
        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!


        val userEmail: String? = pref.getString("userEmail","non Email")
//
//        calenderItems.add(ItemCalenderVO("2022년6월8일","기태랑 구글가기", user_email = userEmail))
//        calenderItems.add(ItemCalenderVO("2022년7월8일","i태랑 구글가기", user_email = userEmail))
//        calenderItems.add(ItemCalenderVO("2022년9월8일","기태ii가기", user_email = userEmail))
//

        fragmentBinding.btn.setOnClickListener {
            val intent = Intent(requireContext(), RecordActivity::class.java)
            startActivity(intent)
        }

        // 일단 테스트 목적으로 아이템 만들어두자.
        items.add(ItemTab2First("D+1","2022년 4월 26일", R.drawable.siba))
        items.add(ItemTab2First("D+3","2022년 4월 28일", R.drawable.siba ))
        items.add(ItemTab2First("D+5","2022년 4월 30일", R.drawable.siba))

        recyclerView = fragmentBinding.recycler
        fragmentBinding.recycler.adapter = childFragmentManager?.let{ RecyclerAdaopterTab2First(activity as Context, items , it) }



    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recycler.adapter?.notifyDataSetChanged()
    }


    private val listener: OnSelectDateListener = OnSelectDateListener {

    }

}