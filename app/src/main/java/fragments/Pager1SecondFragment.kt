package fragments

import Network.RetrofitHelper
import Network.RetrofitService
import adapters.RecyclerAdaopterTab1
import android.app.Activity
import android.content.Context
import android.content.Context.*
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.CustomDialogBinding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
import model.CustomDialog
import model.ScheduleVO


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.days

// 달력 부분!!!
class Pager1SecondFragment : Fragment() {

    lateinit var fragmentBinding: FragmentSecondPager1Binding
    lateinit var recyclerView: RecyclerView
    val calenderItems = mutableListOf<ScheduleVO>()
    lateinit var title: String
    val userEmail by lazy { pref.getString("userEmail", "").toString() }

    val retrofitHelper = RetrofitHelper.getRetrofitInstans()
    val retrofitService = retrofitHelper!!.create(RetrofitService::class.java)

    lateinit var pref: SharedPreferences
    val dialogBinding: CustomDialogBinding by lazy {
        CustomDialogBinding.inflate(
            LayoutInflater.from(
                context
            )
        )
    }

    val datePickerBuilder: DatePickerBuilder by lazy {
        DatePickerBuilder(
            requireContext(),
            listener
        ).setPickerType(CalendarView.ONE_DAY_PICKER)
    }
    val datePicker: DatePicker by lazy { datePickerBuilder.build() }
    val calendarView: CalendarView by lazy { fragmentBinding.calenderView }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentSecondPager1Binding.inflate(inflater, container, false)

        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var user_email = pref.getString("userEmail", "non Email")

        // 이벤트 데이들
        val list = ArrayList<EventDay>()

        recyclerView = fragmentBinding.recyclerTab1
        fragmentBinding.recyclerTab1.adapter = childFragmentManager.let {
            RecyclerAdaopterTab1(activity as Context, calenderItems, it)

        }


        // 오늘 날짜

        var now = System.currentTimeMillis()
        var year = SimpleDateFormat("yyyy", Locale.KOREAN).format(now).toInt()
        var month = SimpleDateFormat("MM", Locale.KOREAN).format(now).toInt()
        var day = SimpleDateFormat("dd", Locale.KOREAN).format(now).toInt()


        // 그럼 우리가 저장할 때 마다 이거 쓰잖아?

        // 롱클릭 리스너 람다식 표기법 사용하는 법
        // https://workingdev.net/android,/kotlin/2018/08/01/handling-clicks-and-long-clicks.html

        calendarView.setOnDayClickListener {

            val builder = AlertDialog.Builder(context as Activity)
                .setNegativeButton("기록하기",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        //기록하기 버튼을 누르면 내가 만든 커스텀 다이알로그 보여주기
                        val clickedDay = it.calendar
                        list.add(it)
                        list.get(0).calendar

                        Log.i(
                            "날짜 선택함 !!!!",
                            list.get(0).calendar.time.toString()
                        ) ///  이거다 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


                        val dialog = CustomDialog(requireContext())
                        dialog.myDialog()
                        dialog.setOnClickListener(object : CustomDialog.ButtonClickListener {
                            override fun onClicked(item: ScheduleVO) {
                                Log.i("tttttttttt", "성공" + "히히히히ㅣㅎㅎ")


                            }
                        })


                    })
                .setNeutralButton("일정보기", DialogInterface.OnClickListener { dialogInterface, i ->
                    // 일정보기 버튼을 누르면 리사이클러로 그 날의 일정을 보여줌
                    // 서버에서 그 날의 일정을 가져와서 보여줘야 함.!!!!!

                    // 레트로핏 서비스로 !!


                })
                .show()

        }


    }// onViewCreated


    fun showKeyboardFrom(view: View) { // 키보드가 보여질 때
        val manager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(dialogBinding.etTitle, InputMethodManager.SHOW_FORCED)
    }

    private val listener: OnSelectDateListener = OnSelectDateListener {
        Toast.makeText(context, "날짜를 클릭하셨음", Toast.LENGTH_SHORT).show()
    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

    }


}



