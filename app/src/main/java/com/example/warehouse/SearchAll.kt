package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class SearchAll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_all)
        val viewpager2 = findViewById<ViewPager2>(R.id.vp_product)
        val adapter = ProdFragementAdapter(supportFragmentManager,lifecycle)

        viewpager2.adapter=adapter
    }
}