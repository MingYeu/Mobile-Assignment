package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class InternalMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_map)

        val internalMaps:ImageView = findViewById(R.id.internal_maps)
        internalMaps.setImageResource(R.drawable.warehouse_internal)
    }
}