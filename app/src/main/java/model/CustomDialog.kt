package model

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.*
import androidx.preference.PreferenceManager
import com.wookie_soft.inah.R
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


        val title = dialog.findViewById<EditText>(R.id.et_title).toString()
        val location = dialog.findViewById<EditText>(R.id.et_location).toString()
        val file = dialog.findViewById<TextView>(R.id.tv_file).toString()
        val msg = dialog.findViewById<EditText>(R.id.et_msg).toString()
        val friends = dialog.findViewById<EditText>(R.id.et_friends).toString()
        val check = dialog.findViewById<CheckBox>(R.id.check_share)


        val okBtn = dialog.findViewById<Button>(R.id.btn_ok)
        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cencle)


        okBtn.setOnClickListener {
            val userEmail:String = pref.getString("userEmail","inahpakkr@gmail.com").toString()
            var item = ScheduleVO(userEmail,"2022",title,msg,location,location,file,"true")
            onClickListener.onClicked(item)
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

}



