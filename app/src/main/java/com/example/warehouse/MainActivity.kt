package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

//import com.example.warehouse.DatabaseHelper as db

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonIn: ImageButton = findViewById(R.id.btn_stock_in)
        buttonIn.setOnClickListener {
            val intent = Intent(this, ProductScan::class.java)
            intent.putExtra("stock_in_out", "1")
            startActivity(intent)
        }

        val buttonOut: ImageButton = findViewById(R.id.btn_stock_out)
        buttonOut.setOnClickListener {
            val intent = Intent(this, ProductScan::class.java)
            intent.putExtra("stock_in_out", "2")
            startActivity(intent)
        }

        val btnReport: ImageButton=findViewById(R.id.btn_report)
        btnReport.setOnClickListener{
            val intent = Intent(this, Report::class.java)
            startActivity(intent)
        }

        val btnWarehouse: ImageButton=findViewById(R.id.btn_warehouse)
        btnWarehouse.setOnClickListener{
            val intent = Intent(this, warehouse::class.java)
            startActivity(intent)
        }

        val btnSearch: Button =findViewById(R.id.btn_search)
        btnSearch.setOnClickListener{
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }

//        var helper = DBHelper(applicationContext)
//        var db = helper.readableDatabase
//        var rs = db.rawQuery("SELECT * FROM PRODUCTS", null)
//
//        if(rs.moveToNext())
//            Toast.makeText(applicationContext, rs.getString(1), Toast.LENGTH_LONG).show()
//
//        val abc = findViewById<EditText>(R.id.txt)
//
//        val but = findViewById<Button>(R.id.btn)
//        but.setOnClickListener{
//            var cv = ContentValues()
//            cv.put("NAME", abc.text.toString())
//            db.insert("PRODUCTS", null, cv)
//
//            abc.setText("")
//            abc.requestFocus()



//            var abc = findViewById<TextView>(R.id.txt)
//            var helper2 = DBHelper(applicationContext)
//            var db2 = helper2.readableDatabase
//            val args = listOf(abc.text.toString()).toTypedArray()
//            val rs2 = db2.rawQuery("SELECT * FROM USERS WHERE NAME = ?", args)
//
//            if(rs2.moveToNext())
//                Toast.makeText(applicationContext, rs2.getString(1), Toast.LENGTH_LONG).show()
//            else
//                Toast.makeText(applicationContext, "cancel", Toast.LENGTH_LONG).show()

//        }
    }

    override fun onBackPressed() {

    }


}