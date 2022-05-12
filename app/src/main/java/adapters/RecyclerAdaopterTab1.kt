package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.RecyclerTap1Binding
import model.ItemVO

class RecyclerAdaopterTab1(val context: Context, var items: MutableList<ItemVO>, private val fragmentManager: FragmentManager): RecyclerView.Adapter<RecyclerAdaopterTab1.VH>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding:RecyclerTap1Binding = RecyclerTap1Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItemId(position)

        //여기서 아이템뷰 이벤트를 쓰자 !
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "이걸 클릭하면 다이알로그를 띄우자.나중에..", Toast.LENGTH_SHORT).show()
        }
        // 여기서 클릭 이벤트 처리해야 하나? ㄴㄴ 프레그먼트 가자..


//
//        holder.dDay.setText( items[position].date.toString())
//        holder.day.setText( items[position].date.toString())
        holder.time.setText(items[position].user_email.toString())
        holder.title.setText( items[position].title.toString())

    }

    override fun getItemCount(): Int =  items.size

    inner class VH(val binding: RecyclerTap1Binding) :RecyclerView.ViewHolder(binding.root){

        val dDay:TextView = binding.tvDate
        val day:TextView = binding.tvDate
        val time:TextView = binding.tvTime
        val title:TextView = binding.tvTitle

    }

}