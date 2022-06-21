package adapters

import activities.NewsUrlActivity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.R
import com.wookie_soft.inah.databinding.RecyclerTap3Binding
import model.NaverApiItems

class RecyclerFragmentAdapterTap3(val context: Context, var datas: MutableList<NaverApiItems>) : RecyclerView.Adapter<RecyclerFragmentAdapterTap3.VH>(){

    inner class VH(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding:RecyclerTap3Binding = RecyclerTap3Binding.bind(itemView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.recycler_tap3,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = datas[position]

        // <b></b> 태그 제거
        var mtitle = datas[position].title.replace("<b>","").replace("</b>","")
        var mdescription = datas[position].description.replace("<b>","").replace("</b>","")

        holder.binding.tvTitle.text = mtitle
        holder.binding.tvDescription.text = mdescription
        holder.binding.tvPubDate.text = datas[position].pubDate

        holder.itemView.setOnClickListener {
            val intent: Intent = Intent(context, NewsUrlActivity::class.java)
            intent.putExtra("newsUrl",datas[position].link)
            Log.i(" 아이템 뷰 클릭시 uri ",datas[position].link )
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = datas.size



}