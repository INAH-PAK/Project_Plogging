package model

import G
import Network.RetrofitHelper
import Network.RetrofitService
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.applandeo.materialcalendarview.CalendarUtils
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.chip.Chip
import com.wookie_soft.inah.R
import com.wookie_soft.inah.R.layout
import fragments.Pager1SecondFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.typeOf


// 달력에 일정 기입을 위한 커스텀 뷰
class CustomDialog(context: Context) : AlertDialog(context) {
    val dialog = Dialog(context)
    val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var position = 0

    var pickerDate:String = "null"
    var pickerTime:String = "null"
    val calendarList = mutableListOf<Calendar>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun myDialog() {
        dialog.setContentView(layout.custom_dialog)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val title = dialog.findViewById<EditText>(R.id.et_title)
        val location = dialog.findViewById<EditText>(R.id.et_location)
        val file = dialog.findViewById<TextView>(R.id.tv_file)
        val msg = dialog.findViewById<EditText>(R.id.et_msg)
        val friends = dialog.findViewById<EditText>(R.id.et_friends)
        val check = dialog.findViewById<CheckBox>(R.id.check_share) // 뺄까
        val dateStart = dialog.findViewById<Chip>(R.id.chip_day_start)
        val datEnd = dialog.findViewById<Chip>(R.id.chip_day_end)
        val timeStart = dialog.findViewById<Chip>(R.id.chip_time_start)
        val timeEnd = dialog.findViewById<Chip>(R.id.chip_time_end)

        val okBtn = dialog.findViewById<Button>(R.id.btn_ok)
        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cencle)

        val formDate = SimpleDateFormat("yyyy. MM. dd")
        val formTime = SimpleDateFormat("HH : mm")


        // 다이알로그가 떴다가 죽으면,
        dialog.setOnDismissListener {
            Log.i("다이알로그 커스텀 클래스에서 죽음"," 다이알로그 커스텀 클래스에서 죽음")


            Pager1SecondFragment.noti()
        }



        // 날짜 시간 Picker 리스너
        dateStart.setOnClickListener {
            val builder = DatePickerBuilder(context, OnSelectDateListener {

                pickerDate= it.get(it.size).time.toString()

                Log.i("선택한 날짜", it.get(0).time.toString()) // 이거임.
                Log.i("선택한 날짜", it.get(0).time.toString()[0].toString())
                Log.i("선택한 날짜", it.get(0).time.toString()[1].toString())
                Log.i("선택한 날짜", it.get(0).time.toString()[2].toString())

                dateStart.setText(formDate.format(it.get(0).time).toString())
        }).setPickerType(CalendarView.ONE_DAY_PICKER).build().show()
        }
        datEnd.setOnClickListener {
            val builder = DatePickerBuilder(context, OnSelectDateListener {

                Log.i("선택한 날짜", it.get(0).time.toString())
                datEnd.setText(formDate.format(it.get(0).time).toString())
            }).setPickerType(CalendarView.ONE_DAY_PICKER).build().show()
        }
        timeStart.setOnClickListener {
            val picker:TimePickerDialog = TimePickerDialog(context,TimePickerDialog.OnTimeSetListener { timePicker, i, i2 -> // i : 시 , i2 :  분
                var t = String.format("%02d",i)
                    timeStart.setText("$t : $i2")
                pickerTime = String.format("%02d",i) +" : "+ timeStart.setText("$t : $i2")
            },0,0,true)

            picker.show()
        }
        timeEnd.setOnClickListener {
            val picker:TimePickerDialog = TimePickerDialog(context,TimePickerDialog.OnTimeSetListener { timePicker, i, i2 -> // i : 시 , i2 :  분
                var t = String.format("%02d",i)
                timeEnd.setText("$t : $i2")
            },0,0,true)
            picker.show()

        }

        // 확인 버튼
        okBtn.setOnClickListener {
            // 일정 기록 확인버튼. -> 1. 2.
            val userEmail: String? = pref.getString("userEmail", "inahpakkr@gmail.com")
            var t = title.text.toString()

            val item = ScheduleVO(
                "inah@",pickerDate + pickerTime,
                title.text.toString(),
                msg.text.toString(),
                location.text.toString(),
                location.text.toString(),
                file.text.toString(),
                friends.text.toString()
            )
            insertDB(item)

            Pager1SecondFragment.setCalenderViewEventDays()


            //A
            // 프레그먼트 화면의 리사이클러에 추가 함.
            User.glovalItemList.add( 0,item)
            //User.glovalItem = item
            Log.i("글로번 아이템", User.glovalItemList[0].title.toString() )

            dialog.dismiss()



        }

        // 취소 버튼
        cancelBtn.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }


    fun insertDB(item: ScheduleVO) {
        //Post 방식으로 객체를 서버에 전달하자 !

        val retrofit = RetrofitHelper.getRetrofitInstans()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        Log.i("fffffffffffffffffffffffffffffffff", item.title)
        Log.i("데이터는 잘 들어갔나", item.user_email + " ssssss" + item.title)


                //서버로 보낼 값을 가진 call 바로 보내버리기
        val call: Call<String> = retrofitService.postMethodTest(item)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("서버 응답 성공시", response.body() + "")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("서버 응답 실패", t.message + "")
            }

        })
    }

    fun loadDB(){
        //Post 방식으로 객체를 서버에 전달하자 !

    }






}


