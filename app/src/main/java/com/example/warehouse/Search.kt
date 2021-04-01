package com.example.warehouse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Search : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val btnEnter = findViewById<ImageButton>(R.id.btn_search_enter)
        btnEnter.setOnClickListener {
            val intent = Intent(this, SearchEnterProduct::class.java)
            startActivity(intent)
        }

        val btnScan = findViewById<ImageButton>(R.id.btn_search_scan)
        btnScan.setOnClickListener(){
            val intent = Intent(this, SearchScanProduct::class.java)
            startActivity(intent)
        }


    }
}