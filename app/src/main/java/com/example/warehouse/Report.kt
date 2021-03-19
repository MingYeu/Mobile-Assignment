package com.example.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Report : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report2)

        val btnRackInReport: ImageButton =findViewById(R.id.imgBtnInventory)
        btnRackInReport.setOnClickListener{
            val intent = Intent(this, RackInReport::class.java)
            startActivity(intent)
        }

        val btnHisReport: ImageButton=findViewById(R.id.imgBtnHistory)
        btnHisReport.setOnClickListener{
            val intent = Intent(this, HistoryReport::class.java)
            startActivity(intent)
        }

    }
}