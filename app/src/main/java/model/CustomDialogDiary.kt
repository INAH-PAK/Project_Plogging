package model

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.chip.Chip
import com.wookie_soft.inah.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

// 일기 작성시 사용될 다이알로그
class CustomDialogDiary(context: Context) :  AlertDialog(context){

    val dialog = Dialog(context)
    val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @RequiresApi(Build.VERSION_CODES.O)
    fun myShow(){

        val title = dialog.findViewById<EditText>(R.id.et_title)
        val location = dialog.findViewById<EditText>(R.id.et_location)
        val file = dialog.findViewById<TextView>(R.id.tv_file)
        val msg = dialog.findViewById<EditText>(R.id.et_msg)
        val check = dialog.findViewById<CheckBox>(R.id.check_share) // 공유여부 체크박스
        val dateStart = dialog.findViewById<Chip>(R.id.chip_day_start)
        val datEnd = dialog.findViewById<Chip>(R.id.chip_day_end)
        val timeStart = dialog.findViewById<Chip>(R.id.chip_time_start)
        val timeEnd = dialog.findViewById<Chip>(R.id.chip_time_end)

        var mTime: String = LocalDateTime.now().toString()
        var mDate: String = LocalDate.now().toString()


        val formDate = SimpleDateFormat("yyyy. MM. dd")

        dialog.setContentView(R.layout.custom_dialog_diary)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        dateStart.setOnClickListener {
            val builder = DatePickerBuilder(context, OnSelectDateListener {

                mDate= it.get(it.size).time.toString()

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
            val picker: TimePickerDialog = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 -> // i : 시 , i2 :  분
                var t = String.format("%02d",i)
                timeStart.setText("$t : $i2")
                mTime = String.format("%02d",i) +" : "+ timeStart.setText("$t : $i2")
            },0,0,true)

            picker.show()
        }
        timeEnd.setOnClickListener {
            val picker: TimePickerDialog = TimePickerDialog(context,
                TimePickerDialog.OnTimeSetListener { timePicker, i, i2 -> // i : 시 , i2 :  분
                var t = String.format("%02d",i)
                timeEnd.setText("$t : $i2")
            },0,0,true)
            picker.show()

        }
        dialog.create()
        dialog.show()


    }
}