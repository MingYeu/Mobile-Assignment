package com.example.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class RackInReport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rack_in_report)

        val btnInvReport: Button =findViewById(R.id.btnInvReport)
        btnInvReport.setOnClickListener{
            val intent = Intent(this, InventoryReport::class.java)
            startActivity(intent)
        }

    }
}