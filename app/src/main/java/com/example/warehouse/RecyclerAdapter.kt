package com.example.warehouse

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var itemList:List<TrackItem>):RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       val itemName:TextView = itemView.findViewById(R.id.tv_title)
        val itemStatus:TextView = itemView.findViewById(R.id.tv_description)
//        val itemQty:TextView = itemView.findViewById(R.id)
        val fromWarehouse:TextView = itemView.findViewById(R.id.tv_warehouse)
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,TrackDetailActivity::class.java)
                //intent.putExtra("ItemName",itemName.toString())
                itemView.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(v)
    }



    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
       val currentItem = itemList[position]
        holder.itemName.setText(currentItem.itemName)
        holder.itemStatus.setText(currentItem.status)
        holder.fromWarehouse.setText(currentItem.fromWarehouse)

    }



}