package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: ImageButton = findViewById(R.id.btn_stock_in)
        button.setOnClickListener {
            val intent = Intent(this, ProductScan::class.java)
            startActivity(intent)
        }
    }


}