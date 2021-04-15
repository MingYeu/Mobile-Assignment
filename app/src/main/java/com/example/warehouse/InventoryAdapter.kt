package com.example.warehouse

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InventoryAdapter (private var prodList: List<Inventory>, val context: Context) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>(){
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val tvProdName: TextView = item.findViewById(R.id.tvProdname)
        val tvProdId: TextView = item.findViewById(R.id.tvProdID)
        val tvProdQty: TextView = item.findViewById(R.id.tvProdqty)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.inventory_list, parent, false)
        return InventoryAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = prodList[position]
        holder.tvProdName.setText(item.name)
        holder.tvProdId.setText("ID: " +item.StockId)
        holder.tvProdQty.setText("Quantity: " +item.quantity)

    }

    override fun getItemCount(): Int {
        return prodList.size
    }

}