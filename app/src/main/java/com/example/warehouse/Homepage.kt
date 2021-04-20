package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity

//import com.example.warehouse.DatabaseHelper as db

class Homepage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val username = intent.getStringExtra("uname")
        val type = intent.getStringExtra("userType")

        val btnMyProfile = findViewById<ImageView>(R.id.btnMyProfile)
        btnMyProfile.setOnClickListener(){
            val intent = Intent(this, MyProfile::class.java)
            intent.putExtra("userType", type);
            intent.putExtra("uname", username);
            startActivity(intent)
        }

        val btnLogOut = findViewById<ImageView>(R.id.btnLogout)
        btnLogOut.setOnClickListener(){
            val intent = Intent(this, LogIn::class.java)
            intent.putExtra("userType", "");
            intent.putExtra("uname", "");
            startActivity(intent)
        }


        // val buttonIn: ImageButton = findViewById(R.id.btn_stock_in)
        val buttonIn: CardView = findViewById(R.id.btn_stock_in)
        buttonIn.setOnClickListener {
            val intent = Intent(this, ProductScan::class.java)
            intent.putExtra("stock_in_out", "1")
            startActivity(intent)
        }

        //val buttonOut: ImageButton = findViewById(R.id.btn_stock_out)
        val buttonOut: CardView = findViewById(R.id.btn_stock_out)
        buttonOut.setOnClickListener {
            val intent = Intent(this, ProductScan::class.java)
            intent.putExtra("stock_in_out", "2")
            startActivity(intent)
        }

        //val btnReport: ImageButton=findViewById(R.id.btn_report)
        val btnReport: CardView=findViewById(R.id.btn_report)
        btnReport.setOnClickListener{
            val intent = Intent(this, Report::class.java)
            startActivity(intent)
        }

        //val btnWarehouse: ImageButton=findViewById(R.id.btn_warehouse)
        val btnWarehouse: CardView=findViewById(R.id.btn_warehouse)
        btnWarehouse.setOnClickListener{
            val intent = Intent(this, warehouse::class.java)
            startActivity(intent)
        }

        //val btnSearch: Button =findViewById(R.id.btn_search)
        val btnSearch: ImageButton =findViewById(R.id.btn_search)
        btnSearch.setOnClickListener{
            val intent = Intent(this, Search::class.java)
            intent.putExtra("userType", type);
            startActivity(intent)
        }


    }

    override fun onBackPressed() {

    }


}