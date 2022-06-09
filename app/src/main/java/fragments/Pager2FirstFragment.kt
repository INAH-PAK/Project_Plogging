package fragments

import activities.RecordActivity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.R
import adapters.RecyclerAdaopterTab2First
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.wookie_soft.inah.databinding.CustomDialogBinding
import com.wookie_soft.inah.databinding.FragmentFirstPager2Binding
import model.Borad
import model.ItemTab2First
import model.User

class Pager2FirstFragment : Fragment() {
    lateinit private var  fragmentBinding:FragmentFirstPager2Binding
    lateinit var recyclerView: RecyclerView
    var items = mutableListOf<Borad>()
    lateinit var borrrrItem:Borad

    lateinit var tt:Borad


    lateinit var pref: SharedPreferences


    val datePickerBuilder: DatePickerBuilder by lazy {
        DatePickerBuilder(requireContext(),listener ).setPickerType(
            CalendarView.ONE_DAY_PICKER)}
    val datePicker: DatePicker by lazy {  datePickerBuilder.build() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentFirstPager2Binding.inflate( inflater , container , false )
        pref = PreferenceManager.getDefaultSharedPreferences(requireContext())

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 리사이클러 코드쓰기.
        // 바인딩 사용하니까, 프레그먼트는 바인드 후에 써야 함 !!!

        borrrrItem = Borad(title =" 아직 아무것도 없음")


        if (savedInstanceState != null) {
            tt = savedInstanceState.getSerializable("bored") as Borad
        }



        val userEmail: String? = pref.getString("userEmail","non Email")

        bundleOf()
        fragmentBinding.btn.setOnClickListener {
            val intent = Intent(requireContext(), RecordActivity::class.java)
            startActivity(intent)
        }


        // 일단 테스트 목적으로 아이템 만들어두자.
        items.add(Borad("D+1","2022년 4월 26일","ff","ddd",""))
        items.add(Borad("D+fdgd","2022년 4월 26일","fdff","dddd",""))
        items.add(Borad("D+ewerfe","2022년 4월 26일","fffwdas","dsffdd",""))




        recyclerView = fragmentBinding.recycler
        fragmentBinding.recycler.adapter = childFragmentManager?.let{ RecyclerAdaopterTab2First(activity as Context, items , it) }

     //   https://pancake.coffee/2022/03/28/fragment%EC%97%90%EC%84%9C-%ED%8D%BC%EB%AF%B8%EC%85%98-%EC%9A%94%EC%B2%AD%ED%95%98%EA%B8%B0-2021/
//    ActivityResultLauncher<>

    }







    override fun setArguments(args: Bundle?) { // 프레그먼트 동적으로 생성할때 -> xml로 만들때.
                                                // 프레그먼트의 생성자는 건들이지 말라고 구글이 함. 근데 내가 ㅔ이터 어케 줌 ? --> 아규먼트.
        // https://altongmon.tistory.com/814
        borrrrItem = args?.getSerializable("bored") as Borad

        items.add(Borad("us","2022년 4월 26일","ff",borrrrItem.title,borrrrItem.message))
        var u = Borad(title = borrrrItem.title)
        items.add(u)
        Log.i("아귬ㄴ트", borrrrItem.title+"아무거도 ")
        Log.i("아귬ㄴ트", u.title+"아무거도 ")
        Log.i("아귬ㄴ트", items.get(items.size-1).title)
        User.titlBundel = u.title
        User.j = borrrrItem

        Log.i("전역변수로 값 넣었는데 들어가냐 제발?", User.titlBundel)
        Log.i("전역변수로 값 넣었는데 들어가냐 제발?", User.j.title)
        //  fragmentBinding.recycler.adapter?.notifyDataSetChanged() -> error :  kotlin.UninitializedPropertyAccessException
//        ActivityResultLauncher 이거
    }



    // 화면 갱신시 리사이클러뷰 초기화
    override fun onResume() {
        super.onResume()
        //
        // 영구 데이터는 반드시 여기에서 저장 ! 구글에서 onSaveInstanceState 에서 저장 권장x
        Log.i("여기는 온 리쥼 !!!의 전역변수값 찍어본거", User.titlBundel)
        Log.i("여기는 온 리쥼 !!!의 전역변수값 찍어본거", User.j.title)
        if(User.isisis == true)  {
            items.add(User.j)
            fragmentBinding.recycler.adapter?.notifyDataSetChanged()
        }


        Toast.makeText(requireContext(), "화면 다시 보임", Toast.LENGTH_SHORT).show()

    }



    private val listener: OnSelectDateListener = OnSelectDateListener {

    }

}