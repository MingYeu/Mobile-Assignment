package com.example.warehouse

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.util.*

class Search : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val type = intent.getStringExtra("userType")

        val btnEnter = findViewById<ImageButton>(R.id.btn_search_enter)
        btnEnter.setOnClickListener {
            val intent = Intent(this, SearchEnterProduct::class.java)
            intent.putExtra("userType", type);
            startActivity(intent)
        }

        val btnScan = findViewById<ImageButton>(R.id.btn_search_scan)
        btnScan.setOnClickListener(){
            val intent = Intent(this, SearchScanProduct::class.java)
            intent.putExtra("userType", type);
            startActivity(intent)
        }

        val btn_search_all = findViewById<ImageButton>(R.id.btn_search_all)
        btn_search_all.setOnClickListener(){
            val intent = Intent(this, SearchAll::class.java)
            startActivity(intent)

        }
    }
}