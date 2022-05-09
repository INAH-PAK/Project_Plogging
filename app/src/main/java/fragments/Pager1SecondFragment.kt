package fragments

import Network.RetrofitHelper
import Network.RetrofitService
import adapters.RecyclerAdaopterTab1
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
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
import model.ItemCalenderVO
import model.MySharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// 달력 부분!!!
class Pager1SecondFragment : Fragment() {

    lateinit var fragmentBinding:FragmentSecondPager1Binding
    lateinit var recyclerView: RecyclerView
    var calenderItems = mutableListOf<ItemCalenderVO>()

    lateinit var pref: MySharedPreference
    lateinit var user_email:String

    // 바텀시트

    lateinit var btmBinding:BottomDialogCalenderBinding
    val datePickerBuilder: DatePickerBuilder by lazy {DatePickerBuilder(requireContext(),listener ).setPickerType(CalendarView.ONE_DAY_PICKER)}
    val datePicker: DatePicker by lazy {  datePickerBuilder.build() }

    lateinit var title:String

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

        pref =MySharedPreference(requireContext())
        user_email = pref.getString("userEmail","non Email")


        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!



        // 일단 테스트 목적으로 아이템 만들어두자.
//        items.add(ItemCalender("D-1","2022년 4월 26일", "오후 6시"," 길동이랑 만나기"))
//        items.add(ItemCalender("D-3","2022년 4월 28일","오후66시"," 춘향이랑 아침산책"))
//        items.add(ItemCalender("D-5","2022년 4월 30일", "오후8시"," 가족들과 저녁후 산책"))

        recyclerView = fragmentBinding.recyclerTab1
        fragmentBinding.recyclerTab1.adapter = childFragmentManager?.let{ RecyclerAdaopterTab1(activity as Context, calenderItems, it) }

        var bottomSheetView= LayoutInflater.from(context).inflate(R.layout.bottom_dialog_calender,null)
        var bottomSheetDialog= BottomSheetDialog(requireContext())
        btmBinding =  BottomDialogCalenderBinding.bind(bottomSheetView)
        // 캘린더뷰 롱클릭 -> 다이알로그로 일정 기입하기.


        // 먼저 키패드 띄우기
        bottomSheetDialog.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        // 키패드의 입력 버튼을 누르면
        btmBinding.etTitle.setOnEditorActionListener { v,actionId, event ->
            var handled = false
            return@setOnEditorActionListener when(actionId){
                EditorInfo.IME_ACTION_DONE ->{
                    title = btmBinding.etTitle.text.toString()
                    btmBinding.etTitle.setText("")
                    Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show()
                    val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(btmBinding.etTitle.windowToken, 0)
                    handled = true
                    bottomSheetDialog.dismiss()
                    return@setOnEditorActionListener true
                }
                else -> false
            }
        }

//            bottomSheetDialog.setOnShowListener {
//                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                btmBinding.etTitle.requestFocus()
//                manager.showSoftInput(btmBinding.etTitle, InputMethodManager.SHOW_FORCED)
//            }

        btmBinding.tv01.setOnClickListener { datePicker.show() }
        btmBinding.tv02.setOnClickListener { datePicker.show() }

        // 일정 추가하기 버튼 클릭이벤트...
        btmBinding.btn.setOnClickListener {

            // 여기서 아이템 리사이클러 만들고, 레트로핏으로 보내야 함.
            // 일단 예시로 ..
            calenderItems.add(ItemCalenderVO("2022년 4월 8일",title,btmBinding.etMsg.text.toString(),user_email))
            bottomSheetDialog.dismiss()

        }// clickSave()


        val calendarView:CalendarView = fragmentBinding.calenderView
        calendarView.setOnDayClickListener{
            val clickedDay = it.calendar
            Log.i("날짜 선택함 !!!!",clickedDay.toString())
                // 다이알로그 띄우기 ...
                // 바텀시트 다이알로그로 만들쟝

            bottomSheetDialog.setContentView(bottomSheetView)
            showKeyboardFrom(bottomSheetView)
            bottomSheetDialog.show()
        }

        bottomSheetDialog.setOnDismissListener {
            fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()
        }


    }

    fun showKeyboardFrom( view: View){
        val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        btmBinding.etTitle.requestFocus()
        manager.showSoftInput(btmBinding.etTitle, InputMethodManager.SHOW_FORCED)
    }


    fun clickSave(){

        calenderItems.add( ItemCalenderVO("2022년 테스트",btmBinding.etTitle.toString(),btmBinding.etMsg.toString(), user_email = pref.getString("userEmail","non Email")))

        val retrofit = RetrofitHelper.getRetrofitInstance()
        val retrofitService = retrofit!!.create(RetrofitService::class.java)

        //  no	user_email	date	title	message	 location_latitude	location_longitude	file

//        var dataPart  = HashMap<String, String>()
//        dataPart.put("user_email", user_email)
//        dataPart.put("date", " 2022년 오늘");
//        dataPart.put("title", bottomSheetLayoutBinding.etTitle.toString());
//        dataPart.put("message", bottomSheetLayoutBinding.etMsg.toString());
//        dataPart.put("location_latitude", "6.3");
//        dataPart.put("location_longitude", "7.1");


        val call= retrofitService.postCalenderDataToServer(ArrayList(calenderItems))

        call.enqueue(object : Callback<ArrayList<ItemCalenderVO>>{
            override fun onResponse(
                call: Call<ArrayList<ItemCalenderVO>>,
                response: Response<ArrayList<ItemCalenderVO>>
            ) {
               calenderItems.clear()
                fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

                val list = response.body()!!
                for(ItemCalender in list){
                    if(ItemCalender != null){
                        calenderItems.add(0,
                            ItemCalenderVO("2022callcallcall","테스트제목","메세지 테스트","inahpakkr@rrr.rrr")
                        )
                    }
                    fragmentBinding.recyclerTab1.adapter?.notifyItemInserted(0)
                }
            }
            override fun onFailure(call: Call<ArrayList<ItemCalenderVO>>, t: Throwable) {
                AlertDialog.Builder(context as Activity).setMessage(t.message).create().show()
            }

        })


    }



    private val listener:OnSelectDateListener = OnSelectDateListener {


    }

    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        fragmentBinding.recyclerTab1.adapter?.notifyDataSetChanged()

    }


}
