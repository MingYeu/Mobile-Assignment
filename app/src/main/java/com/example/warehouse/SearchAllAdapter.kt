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

class SearchAllAdapter(private var prodList: List<Stock>, val context: Context) : RecyclerView.Adapter<SearchAllAdapter.ViewHolder>(){
    class ViewHolder(item: View) : RecyclerView.ViewHolder(item){
        val tvProName: TextView = item.findViewById(R.id.tvProName)
        val tvProId: TextView = item.findViewById(R.id.tvProId)
        val tvProRack: TextView = item.findViewById(R.id.tvProRack)
        val tvProQty: TextView = item.findViewById(R.id.tvProQty)
        val productImage: ImageView = item.findViewById(R.id.productImage)


        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_product, parent, false)
        return SearchAllAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = prodList[position]
        holder.tvProName.setText(item.name)
        holder.tvProId.setText("ID: " +item.StockId)
        holder.tvProRack.setText("Rack: " +item.rack)
        holder.tvProQty.setText("Quantity: " +item.quantity)

        val imageBytes = Base64.decode(item.image, 0)
        val img = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.productImage.setImageBitmap(img)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DisplayStock::class.java)
            intent.putExtra("productID", item.StockId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return prodList.size
    }

}