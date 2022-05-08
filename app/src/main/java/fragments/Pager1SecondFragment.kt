package fragments

import adapters.RecyclerAdaopterTab1
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.BottomDialogCalenderBinding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
import model.ItemCalender


// 달력 부분!!!
class Pager1SecondFragment : Fragment() {

    lateinit var fragmentBinding:FragmentSecondPager1Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<ItemCalender>()

    // 바텀시트
    val bottomSheetView by lazy {  LayoutInflater.from(context).inflate(R.layout.bottom_dialog_calender,null)}
    val bottomSheetDialog by lazy { BottomSheetDialog(requireContext()) }
    val bottomSheetLayoutBinding by lazy { BottomDialogCalenderBinding.bind(bottomSheetView) }
    val datePickerBuilder: DatePickerBuilder by lazy {DatePickerBuilder(requireContext(),listener ).setPickerType(CalendarView.ONE_DAY_PICKER)}
    val datePicker: DatePicker by lazy {  datePickerBuilder.build() }

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
//        items.add(ItemCalender("D-1","2022년 4월 26일", "오후 6시"," 길동이랑 만나기"))
//        items.add(ItemCalender("D-3","2022년 4월 28일","오후66시"," 춘향이랑 아침산책"))
//        items.add(ItemCalender("D-5","2022년 4월 30일", "오후8시"," 가족들과 저녁후 산책"))

        recyclerView = fragmentBinding.recyclerTab1
        fragmentBinding.recyclerTab1.adapter = childFragmentManager?.let{ RecyclerAdaopterTab1(activity as Context, items, it) }

        // 캘린더뷰 롱클릭 -> 다이알로그로 일정 기입하기.
        val calendarView:CalendarView = fragmentBinding.calenderView
        calendarView.setOnDayClickListener{
            val clickedDay = it.calendar
            Log.i("날짜 선택함 !!!!",clickedDay.toString())
                // 다이알로그 띄우기 ...
                // 바텀시트 다이알로그로 만들쟝
            bottomSheetDialog.show()

            bottomSheetLayoutBinding.tv01.setOnClickListener { datePicker.show() }
            bottomSheetLayoutBinding.tv02.setOnClickListener { datePicker.show() }
            bottomSheetLayoutBinding.btn.setOnClickListener {
                // 여기서 아이템 리사이클러 만들고, 레트로핏으로 보내야 함.
                // 일단 예시로 ..
            items.add(ItemCalender("D-1","2022년 4월 26일", "오후 6시"," 길동이랑 만나기"))
            items.add(ItemCalender("D-3","2022년 4월 28일","오후66시"," 춘향이랑 아침산책"))
            items.add(ItemCalender("D-5","2022년 4월 30일", "오후8시"," 가족들과 저녁후 산책"))

            }
            bottomSheetDialog.setContentView(bottomSheetView)



        }


    }

    private val listener:OnSelectDateListener = OnSelectDateListener {

    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

    }


}