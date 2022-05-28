package com.wookie_soft.inah.fragments

import com.wookie_soft.inah.network.RetrofitHelper
import com.wookie_soft.inah.network.RetrofitService
import com.wookie_soft.inah.adapters.RecyclerAdaopterTab1
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
import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
import com.wookie_soft.inah.model.CustomDialog
import com.wookie_soft.inah.model.ScheduleVO
import com.wookie_soft.inah.model.User


import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// 달력 부분!!!
class Pager1SecondFragment : Fragment() {


    lateinit var recyclerView: RecyclerView
    val calenderItems = mutableListOf<ScheduleVO>()

    lateinit var title: String
    val userEmail by lazy { pref.getString("userEmail", "").toString() }
    val dialog by lazy { CustomDialog(requireContext())  }

    val retrofitHelper = RetrofitHelper.getRetrofitInstans()
    val retrofitService = retrofitHelper!!.create(RetrofitService::class.java)

    companion object{

        private lateinit var fragmentBinding: FragmentSecondPager1Binding

        fun noti(){
            fragmentBinding.recyclerTab2.adapter?.notifyDataSetChanged()
        }
    }

    lateinit var pref: SharedPreferences
    val datePickerBuilder: DatePickerBuilder by lazy {
        DatePickerBuilder(
            requireContext(),
            listener
        ).setPickerType(CalendarView.ONE_DAY_PICKER)
    }
    val datePicker: DatePicker by lazy { datePickerBuilder.build() }

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


        var user_email = pref.getString("userEmail", "non Email")

        // 이벤트 데이들
        var events = ArrayList<EventDay>()

        // 달력 밑의 리사이클러
        recyclerView = fragmentBinding.recyclerTab2fkjlksodwijdqlwnfiurbglKJEGM
        fragmentBinding.recyclerTab2.adapter = childFragmentManager.let {
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

                        dialog.myDialog()



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
        fragmentBinding.recyclerTab2.adapter?.notifyDataSetChanged()

    }


}



