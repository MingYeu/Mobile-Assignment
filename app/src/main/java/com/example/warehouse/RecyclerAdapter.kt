package com.example.warehouse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var title:List<String>,private var detail:List<String>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
       val itemDetails: TextView = itemView.findViewById(R.id.tv_description)

        init {
            itemView.setOnClickListener{ v: View ->
                val position:Int=adapterPosition
                Toast.makeText(itemView.context,"Yoou clicked on item # = ${position+1}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(v)
    }



    override fun getItemCount(): Int {
        return title.size;
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       holder.itemTitle.text=title[position]
        holder.itemDetails.text=detail[position]


    }
}