package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SearchEnterProduct  : AppCompatActivity(){

    lateinit var type:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_enter_product)

        type = intent.getStringExtra("userType").toString()

        val btn = findViewById<Button>(R.id.btnEnterProduct)
        btn.setOnClickListener {
            val id = findViewById<TextView>(R.id.ettProductID)
            navigate(id.text.toString())
        }
    }

    private fun navigate(productId: String)
    {
        if(productId.startsWith("S"))
        {
            val intent = Intent(this, DisplayStock::class.java)
            intent.putExtra("productID", productId)
            intent.putExtra("userType", type);
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Wrong Product ID", Toast.LENGTH_LONG).show()
        }
    }
}