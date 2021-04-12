package com.example.warehouse

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private var itemList: List<TrackItem>, val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName: TextView = itemView.findViewById(R.id.tv_title)
        val itemStatus: TextView = itemView.findViewById(R.id.tv_description)
        val fromWarehouse: TextView = itemView.findViewById(R.id.tv_warehouse)

        init {
            itemView.setOnClickListener {

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TrackDetailActivity::class.java)
            intent.putExtra("WarehouseName", currentItem.fromWarehouse)
            intent.putExtra("ItemName", currentItem.itemName)
            intent.putExtra("ItemId", currentItem.id)
            intent.putExtra("ItemStatus", currentItem.status)
            //intent.putExtra(EXTRA_ITEM,currentItem)
            context.startActivity(intent)
        }

    }

}