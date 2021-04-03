package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class warehouse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_warehouse)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewpager2 = findViewById<ViewPager2>(R.id.vp_warehouse)
        val adapter = PageAdapter(supportFragmentManager,lifecycle)

        viewpager2.adapter=adapter
        TabLayoutMediator(tabLayout,viewpager2){tab,position->
            when(position){
                0->tab.text="Track"
                1->tab.text="Search"
                2->tab.text="Map"
                //3->tab.text="Settings"
                else->"Track"
            }
        }.attach()
    }
}