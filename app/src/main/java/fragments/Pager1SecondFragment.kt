package fragments

import Network.RetrofitHelper
import Network.RetrofitService
import activities.MainActivity
import adapters.RecyclerAdaopterTab1
import android.app.Activity
import android.content.Context
import android.content.Context.*
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.CustomDialogBinding
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
import model.CustomDialog
import model.Marker
import model.ScheduleVO
import model.User


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.days

// 달력 부분!!!
class Pager1SecondFragment : Fragment() {


    lateinit var recyclerView: RecyclerView
    val calenderItems = mutableListOf<ScheduleVO>()


    lateinit var title: String
    val userEmail by lazy { pref.getString("userEmail", "").toString() }
    val dialog by lazy { CustomDialog(requireContext() )  }

    val retrofitHelper = RetrofitHelper.getRetrofitInstans()
    val retrofitService = retrofitHelper!!.create(RetrofitService::class.java)

    companion object{

        private lateinit var fragmentBinding: FragmentSecondPager1Binding
        val calendarView: CalendarView by lazy { fragmentBinding.calenderView }

        var calendarInstance = Calendar.getInstance()

        fun setCalenderViewEventDays(){
            G.eventDays.add(EventDay(calendarInstance,R.drawable.ic_baseline_circle_8))

            calendarView.setEvents(G.eventDays)
        }

        fun noti(){
            fragmentBinding.recyclerTab2.adapter?.notifyDataSetChanged()
        }
    }

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView.setEvents(G.eventDays)

        var user_email = pref.getString("userEmail", "non Email")

        // 이벤트 데이들
        val list = ArrayList<EventDay>()

        // 달력 밑의 리사이클러
        recyclerView = fragmentBinding.recyclerTab2
        fragmentBinding.recyclerTab2.adapter = childFragmentManager.let {
            loadDBSchedule()
            RecyclerAdaopterTab1(activity as Context, User.glovalItemList , it)



        }


        // 오늘 날짜

        var now = System.currentTimeMillis()
        var year = SimpleDateFormat("yyyy", Locale.KOREAN).format(now).toInt()
        var month = SimpleDateFormat("MM", Locale.KOREAN).format(now).toInt()
        var day = SimpleDateFormat("dd", Locale.KOREAN).format(now).toInt()


        // 롱클릭 리스너 람다식 표기법 사용하는 법
        // https://workingdev.net/android,/kotlin/2018/08/01/handling-clicks-and-long-clicks.html

        calendarView.setOnDayClickListener {

            Log.i("날짜", calendarView.currentPageDate.toString())
            Log.i("날짜", calendarView.currentPageDate.toString())
            var size = calendarView.selectedDates.size-1
            Log.i("ddd", calendarView.selectedDates[size].toString())

            val builder = AlertDialog.Builder(context as Activity)
                //기록하기 버튼을 누르면 내가 만든 커스텀 다이알로그 보여주기
                .setPositiveButton("기록하기",
                    DialogInterface.OnClickListener { dialogInterface, i ->

                        calendarInstance = it.calendar

                        //아이콘 찍는 코드
//                        G.eventDays.add(EventDay(it.calendar,R.drawable.ic_baseline_circle_8))
//                        calendarView.setEvents(G.eventDays)

                        list.add(it)
                        list.get(0).calendar

                        Log.i(
                            "날짜 선택함 !!!!",
                            list.get(0).calendar.time.toString()
                        ) ///  이거다 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        dialog.myDialog(calendarInstance)

                    })
                .setNeutralButton("일정보기", DialogInterface.OnClickListener { dialogInterface, i ->
                    // 일정보기 버튼을 누르면 리사이클러로 그 날의 일정을 보여줌
                    // 서버에서 그 날의 일정을 가져와서 보여줘야 함.!!!!!

                    // 레트로핏 서비스로 !!
//                    loadDBSchedule()
                })
                .setOnDismissListener {
                    Log.i("프레그먼트에서의 다아알로그 주금 11111 ","ddddddddddddddddddd")
                   loadDBSchedule()
                }
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
        calendarView.setEvents(G.eventDays)

        fragmentBinding.recyclerTab2.adapter?.notifyDataSetChanged()

    }

    private  fun loadDBSchedule(){
        // 서버 DB - Schedule 에서 사용자의 일정들을 불러오기
        val schedule = ArrayList<ScheduleVO>()
        val call : Call<ArrayList<ScheduleVO>> = retrofitService.loadDBScheduleVO()
        call.enqueue( object  : Callback<ArrayList<ScheduleVO>>{
            override fun onResponse(
                call: Call<ArrayList<ScheduleVO>>,
                response: Response<ArrayList<ScheduleVO>>
            ) {
                val mSchedule: ArrayList<ScheduleVO>? = response.body()
                if (mSchedule == null ) return
                else {
                        // 이 값을 아답터에 전달해줘야 함.
                        fragmentBinding.recyclerTab2.adapter = childFragmentManager.let {
                            RecyclerAdaopterTab1(activity as Context, mSchedule , it)
                    }
                    Log.i("dddddddd", " 아답터에 앖 전달함.")
                }

            }
            override fun onFailure(call: Call<ArrayList<ScheduleVO>>, t: Throwable) {
                Toast.makeText(context, " 콜객체 에러. $t", Toast.LENGTH_SHORT).show()
            }

        })

    }



}



