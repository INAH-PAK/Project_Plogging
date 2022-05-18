package model

import Network.RetrofitHelper
import Network.RetrofitService
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.google.android.material.chip.Chip
import com.wookie_soft.inah.R
import com.wookie_soft.inah.R.layout
import com.wookie_soft.inah.databinding.CustomDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.*


// 달력에 일정 기입을 위한 커스텀 뷰
class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    val pref:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

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


        okBtn.setOnClickListener {
            val userEmail: String? = pref.getString("userEmail","inahpakkr@gmail.com")
            var t = title.text.toString()
            val item = ScheduleVO("inah@","2022",title.text.toString(),msg.text.toString(),location.text.toString(),location.text.toString(),file.text.toString(),friends.text.toString())
            clickBtn(item)
            dialog.dismiss()
        }
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        // 날짜 시간 선택 리스너
        dateStart.setOnClickListener {
            myDatePicker()
        }

        dialog.show()
    }

    interface ButtonClickListener{
        fun onClicked(item:ScheduleVO)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: CustomDialog.ButtonClickListener){
        onClickListener = listener
    }





    fun clickBtn( item: ScheduleVO) {
        //Post 방식으로 객체를 서버에 전달하자 !

        //calenderItems.add(item)

        val retrofit = RetrofitHelper.getRetrofitInstans()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        Log.i("fffffffffffffffffffffffffffffffff",item.title)
        Log.i("데이터는 잘 들어갔나", item.user_email + " ssssss" + item.title)

        //서버로 보낼 값을 가진 call 바로 보내버리기
        val call: Call<String> = retrofitService.postMethodTest(item)
            call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i("서버 응답 성공시", response.body()+"")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("서버 응답 실패", t.message+"")
            }

    })

        fun myDatePicker(context:Context){

            lateinit var listener: OnSelectDateListener

            val builder = DatePickerBuilder(context, listener)
                .setPickerType(CalendarView.ONE_DAY_PICKER)
                .build()
                .show()



        }

        fun OnSelectDateListener(){
             fun onSelect(calendar: MutableList<Calendar>?) {
               Log.i("선택한 날짜", calendar!!.get(0).time.toString() )
            }
        }





}
}


