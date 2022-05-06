package com.wookie_soft.inah

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.RecyclerTap1Binding
import com.wookie_soft.inah.databinding.RecyclerTap2FirstBinding
import com.wookie_soft.inah.databinding.RecyclerTap2SecondBinding

class RecyclerAdaopterTab2Second constructor(val context: Context, var items: MutableList<ItemTab2Second>, private val fragmentManager : FragmentManager): RecyclerView.Adapter<RecyclerAdaopterTab2Second.VH>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: RecyclerTap2SecondBinding = RecyclerTap2SecondBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItemId(position)

        //여기서 아이템뷰 이벤트를 쓰자 !
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "이걸 클릭하면 다이알로그를 띄우자.나중에..", Toast.LENGTH_SHORT).show()
        }
        holder.binding.tv01.setText("gg")
        holder.location.setText("요기")

    }

    override fun getItemCount(): Int =  items.size

    inner class VH(val binding: RecyclerTap2SecondBinding) :RecyclerView.ViewHolder(binding.root){

        var dDay:TextView  = binding.tv01
        var iv:ImageView = binding.iv
        var name:TextView = binding.tv02
        var location: TextView = binding.tv03
    }

}