package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextClock
import android.widget.TextView
import org.w3c.dom.Text

class SearchProdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_prod)

        val itemName = intent.getStringExtra("srcName").toString()
        val itemId = intent.getStringExtra("srcId").toString()
        val itemQuantity = intent.getIntExtra("srcQuantity",1).toString()
        val itemPrice = intent.getDoubleExtra("srcPrice",1.0).toString()
        val rackId = intent.getStringExtra("srcRackId").toString()

        val txtProdName:TextView = findViewById(R.id.prodName)
        txtProdName.setText(itemName)
        val txtProd:TextView = findViewById(R.id.prodId)
        txtProd.setText(itemId)
        val txtQty:TextView = findViewById(R.id.prodQty)
        txtQty.setText(itemQuantity)
        val txtPrice:TextView = findViewById(R.id.itemPrice)
        txtPrice.setText(itemPrice)
        val txtRack:TextView = findViewById(R.id.rackId)
        txtRack.setText(rackId)
    }
}