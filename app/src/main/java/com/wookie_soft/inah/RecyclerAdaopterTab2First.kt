package com.wookie_soft.inah

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wookie_soft.inah.databinding.RecyclerTap1Binding

class RecyclerAdaopterTab2First constructor(val context: Context, var items: MutableList<ClipData.Item>): RecyclerView.Adapter<RecyclerAdaopterTab2First.VH>(){

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.recycler_tap1, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var item = items.get(position)

       // holder.recyclerbinding.tv01.setText()
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class VH(itemView: View) :RecyclerView.ViewHolder(itemView){
        lateinit var recyclerbinding:RecyclerTap1Binding



    }

}