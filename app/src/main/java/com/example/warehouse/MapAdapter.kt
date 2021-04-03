package com.example.warehouse

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MapAdapter(private var warehouse:List<MapItem>,val context: Context):
    RecyclerView.Adapter<MapAdapter.MapViewHolder>(){

    inner class MapViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemWarehouse: TextView = itemView.findViewById(R.id.warehouse_title)

//        init {
//            itemView.setOnClickListener { v: View ->
//                val position: Int = adapterPosition
//                Toast.makeText(
//                    itemView.context,
//                    "You clicked on item # = ${position + 1}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapAdapter.MapViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.map_item_layout, parent, false)
        return MapViewHolder(v)
    }


    override fun getItemCount(): Int {
        return warehouse.size
    }

    override fun onBindViewHolder(holder: MapAdapter.MapViewHolder, position: Int) {
        val currentItem = warehouse[position]
        holder.itemWarehouse.text = currentItem.stateName.toString()
        holder.itemView.setOnClickListener {
            val intent = Intent(context,DetailsMapActivity::class.java)
            intent.putExtra("state",currentItem.stateName)
            intent.putExtra("latitud",currentItem.latitud)
            intent.putExtra("longitud",currentItem.longitud)
            //intent.putExtra(EXTRA_ITEM,currentItem)
            context.startActivity(intent)
        }

    }


}