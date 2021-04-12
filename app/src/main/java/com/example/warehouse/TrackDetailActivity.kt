package com.example.warehouse

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class TrackDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_detail)


        val name = intent.getStringExtra("ItemName").toString()
        val warehouseName = intent.getStringExtra("WarehouseName").toString()
        val id = intent.getStringExtra("ItemId").toString()
        val status = intent.getStringExtra("ItemStatus").toString()

        val itemTitle:TextView = findViewById(R.id.item_title)
        val itemId:TextView = findViewById(R.id.item_id)
        val warehouseTitle:TextView = findViewById(R.id.detail_warehouse)

        getSupportActionBar()?.setTitle( name + " : " + id )
        itemTitle.setText(name)
        itemId.setText(id)
        warehouseTitle.setText(warehouseName)

        val circle_1:ImageView = findViewById(R.id.circle_1)
        val circle_2:ImageView = findViewById(R.id.circle_2)
        val circle_3:ImageView = findViewById(R.id.circle_3)
        val circle_4:ImageView = findViewById(R.id.circle_3)

        val line_1:ImageView = findViewById(R.id.line_1)
        val line_2:ImageView = findViewById(R.id.line_2)
        val line_3:ImageView = findViewById(R.id.line_3)

        if(status.equals("shipment_Booked")){
            circle_1.setBackgroundResource(R.drawable.cerclebackgroundgreen)
        }
        else if(status.equals("In_Transit")){
            circle_1.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_2.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            line_1.setImageResource(R.drawable.line_green)
        }
        else if(status.equals("out_Of_Delivery")){
            circle_1.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_2.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_3.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            line_1.setImageResource(R.drawable.line_green)
            line_2.setImageResource(R.drawable.line_green)
        }
        else{
            circle_1.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_2.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_3.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            circle_4.setBackgroundResource(R.drawable.cerclebackgroundgreen)
            line_1.setImageResource(R.drawable.line_green)
            line_2.setImageResource(R.drawable.line_green)
            line_3.setImageResource(R.drawable.line_green)
        }

    }
}