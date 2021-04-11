package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CreateAccSuccess:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_success)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener(){
            val intent = Intent(this,LogIn::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

    }
}