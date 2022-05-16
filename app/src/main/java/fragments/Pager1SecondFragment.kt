    package fragments

    import Network.RetrofitHelper
    import Network.RetrofitService
    import adapters.RecyclerAdaopterTab1
    import android.app.Activity
    import android.content.Context
    import android.content.Context.*
    import android.content.SharedPreferences
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.view.inputmethod.EditorInfo
    import android.view.inputmethod.InputMethodManager
    import android.widget.Toast
    import androidx.appcompat.app.AlertDialog
    import androidx.fragment.app.Fragment
    import androidx.preference.PreferenceManager
    import androidx.recyclerview.widget.RecyclerView
    import com.applandeo.materialcalendarview.CalendarView
    import com.applandeo.materialcalendarview.DatePicker
    import com.applandeo.materialcalendarview.builders.DatePickerBuilder
    import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
    import com.wookie_soft.inah.R
    import com.wookie_soft.inah.databinding.CustomDialogBinding
    import com.wookie_soft.inah.databinding.FragmentSecondPager1Binding
    import model.CustomDialog
    import model.Item
    import model.ScheduleVO

    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response
    import java.text.SimpleDateFormat
    import java.util.*
    import kotlin.collections.ArrayList


    // 달력 부분!!!
    class Pager1SecondFragment : Fragment() {

        lateinit var fragmentBinding: FragmentSecondPager1Binding
        lateinit var recyclerView: RecyclerView
        var calenderItems = mutableListOf<ScheduleVO>()
        lateinit var title: String
        val userEmail by lazy { pref.getString("userEmail","").toString() }

        val retrofitHelper = RetrofitHelper.getRetrofitInstans()
        val retrofitService = retrofitHelper!!.create(RetrofitService::class.java)

        lateinit var pref: SharedPreferences
        val dialogBinding: CustomDialogBinding by lazy {CustomDialogBinding.inflate(LayoutInflater.from(context)) }



        // 다이알로그


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

            recyclerView = fragmentBinding.recyclerTab1
            fragmentBinding.recyclerTab1.adapter = childFragmentManager.let {
                RecyclerAdaopterTab1(
                    activity as Context,
                    calenderItems,
                    it
                )
            }





            // 오늘 날짜

            var now = System.currentTimeMillis()
            var year = SimpleDateFormat("yyyy", Locale.KOREAN).format(now).toInt()
            var month = SimpleDateFormat("MM", Locale.KOREAN).format(now).toInt()
            var day = SimpleDateFormat("dd", Locale.KOREAN).format(now).toInt()

            Log.i(" 이번년도 년도냔돈녿", year.toString() + " 달 : " + month + " : " + day)

            var calender = Calendar.getInstance()
            calender.set(year, month, day)
            calendarView.setDate(calender)


            // 그럼 우리가 저장할 때 마다 이거 쓰잖아?


            calendarView.setOnDayClickListener {
                val clickedDay = it.calendar
                Log.i("날짜 선택함 !!!!", clickedDay.toString())
                // 다이알로그 띄우기 ...
                // 바텀시트 다이알로그로 만들쟝

                val dialog = CustomDialog(requireContext())
                dialog.myDialog()
                dialog.setOnClickListener(object : CustomDialog.ButtonClickListener{
                    override fun onClicked(item: ScheduleVO) {
                        clickBtn(item)
                        Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
                        Log.i("tttttttttt", "성공" + "히히히히ㅣㅎㅎ")
                    }
                })

            }

        }// onViewCreated


    fun showKeyboardFrom(view: View) { // 키보드가 보여질 때
        val manager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(dialogBinding.etTitle, InputMethodManager.SHOW_FORCED)
    }

    private val listener:OnSelectDateListener = OnSelectDateListener {
        Toast.makeText(context, "날짜를 클릭하셨음", Toast.LENGTH_SHORT).show()
    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
    super.onResume()
    fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

    }




   fun clickBtn( item: ScheduleVO){
            //Post 방식으로 객체를 서버에 전달하자 !

       calenderItems.add(item)
       var iiii = item

       val retrofit = RetrofitHelper.getRetrofitInstans()
       val retrofitService = retrofit.create(RetrofitService::class.java)
       Log.i("데이터는 잘 들어갔나", item.user_email +" : "+ item.title)

       //서버로 보낼 값을 가진 call 바로 보내버리기
       val call:Call<ScheduleVO> = retrofitService.postMethodTest(item)
       call.enqueue( object : Callback<ScheduleVO> {
            override fun onResponse(call: Call<ScheduleVO>, response: Response<ScheduleVO>) {
                //성공시

                iiii = response.body()!!

                Log.i("서버에 잘 갔나", item.user_email +" : "+ item.title)

            }

            override fun onFailure(call: Call<ScheduleVO>, t: Throwable) {
                //실패시
                AlertDialog.Builder(requireContext()).setMessage(t.message).create().show()
                Log.i("서버에 잘 갔나", t.message.toString())
            }

        })


   }

}
