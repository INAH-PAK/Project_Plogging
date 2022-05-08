package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.wookie_soft.inah.databinding.FragmentMainTab3Binding

class Tab3MainFragment:Fragment(){
lateinit var  binding: FragmentMainTab3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainTab3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(view).load("https://t1.daumcdn.net/cfile/tistory/99F1234D5E68A39117").into(binding.iv01)

    }
}