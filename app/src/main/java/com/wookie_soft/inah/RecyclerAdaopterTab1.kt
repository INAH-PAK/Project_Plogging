package com.wookie_soft.inah

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.RecyclerTap1Binding

class RecyclerAdaopterTab1 constructor(val context: Context, var items: MutableList<ItemTab1>, private val fragmentManager : FragmentManager): RecyclerView.Adapter<RecyclerAdaopterTab1.VH>(){

    lateinit var recyclerbinding:RecyclerTap1Binding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val inflater: LayoutInflater = LayoutInflater.from(context)
//        val itemView = inflater.inflate(R.layout.recycler_tap1, parent, false)
//
        recyclerbinding = RecyclerTap1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(recyclerbinding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        var item = items.get(position)
        holder.setData(item)
    }

    override fun getItemCount(): Int =  items.size

    inner class VH(private val recyclerbinding: RecyclerTap1Binding) :RecyclerView.ViewHolder(recyclerbinding.root){

        fun setData(item : ItemTab1){
            recyclerbinding.tv01Tab1.setText(item.dDay)
            recyclerbinding.tv02.setText(item.day)
            recyclerbinding.tv03.setText(item.date)
            recyclerbinding.tv04.setText(item.msg)
        }

    }

}