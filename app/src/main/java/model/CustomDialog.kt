package model

import Network.RetrofitHelper
import Network.RetrofitService
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    val pref:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun myDialog() {
        dialog.setContentView(R.layout.custom_dialog)

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
        val check = dialog.findViewById<CheckBox>(R.id.check_share)


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


        //var item = ScheduleVO()

        dialog.show()
    }

    interface ButtonClickListener{
        fun onClicked(item:ScheduleVO)
    }

    private lateinit var onClickListener: ButtonClickListener

    fun setOnClickListener(listener: ButtonClickListener){
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
    }

    }



