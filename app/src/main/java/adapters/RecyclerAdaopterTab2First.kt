package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.RecyclerTap2FirstBinding
import model.Borad
import model.ItemTab2First

class RecyclerAdaopterTab2First constructor(val context: Context, var items: MutableList<Borad>, private val fragmentManager : FragmentManager): RecyclerView.Adapter<RecyclerAdaopterTab2First.VH>(){
    //보드
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: RecyclerTap2FirstBinding = RecyclerTap2FirstBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }



    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItemId(position)



        //여기서 아이템뷰 이벤트를 쓰자 !
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "이걸 클릭하면 다이알로그를 띄우자.나중에..", Toast.LENGTH_SHORT).show()

        }


//        holder.dDay.setText("D-7")
//        holder.binding.tv01.setText("D+" + position)

        holder.dDay.setText(items[position].title.toString())
        holder.day.setText(items[position].date.toString())
        holder.msg.setText(items[position].message.toString())
       // holder.dDay.setText(items[position].iv.toString())


    }

    override fun getItemCount(): Int =  items.size

    inner class VH(val binding: RecyclerTap2FirstBinding) :RecyclerView.ViewHolder(binding.root){
        val dDay:TextView = binding.tv01
        val day:TextView = binding.tv02
        val msg:TextView = binding.tv03
        val img:ImageView = binding.iv


    }

}