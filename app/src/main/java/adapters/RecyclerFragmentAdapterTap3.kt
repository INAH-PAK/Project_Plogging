package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.RecyclerTap2SecondBinding
import com.wookie_soft.inah.databinding.RecyclerTap3Binding
import fragments.Pager1FirstFragment
import fragments.Pager1SecondFragment
import model.ItemTab2Second
import model.NaverApiItems

class RecyclerFragmentAdapterTap3(val context: Context, var datas: MutableList<NaverApiItems>, private val fragmentManager : FragmentManager) : RecyclerView.Adapter<RecyclerFragmentAdapterTap3.VH>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.recycler_tap3,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getItemId(position)

        holder.title.text = datas[position].title
        holder.description.text = datas[position].description
        holder.pubDate.text ="ㅇㅇㅇㅇㅇ"

        holder.itemView.setOnClickListener {
            // 웹 뷰 띄우기
        }



    }

    override fun getItemCount(): Int = datas.size

    inner class VH(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding:RecyclerFragmentAdapterTap3 = RecyclerFragmentAdapterTap3.bind(itemView)

        var title:TextView = binding.tvTitle
        var description:TextView = binding.tvDescription
        var pubDate:TextView = binding.tvPubDate
    }

}