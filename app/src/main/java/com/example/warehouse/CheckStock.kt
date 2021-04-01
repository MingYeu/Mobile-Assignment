package com.example.warehouse

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CheckStock : AppCompatActivity(){

    lateinit var productId : String
    lateinit var page : String

    var hasStock : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_stock)

        val whatPage = intent?.getStringExtra("page")
        page = whatPage.toString()

        val product = intent?.getStringExtra("productID")
        productId = product.toString()

        //Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock").child(productId)

        var getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child("stockId").getValue() == null)
                {
                    haveStock()
                }
                else {
                    hasStock = 1
                    haveStock()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)
    }

    private fun haveStock()
    {
        if(hasStock == 1 && page.equals("1"))
        {
            val intent = Intent(this, StockIn::class.java)
            intent.putExtra("hasStock", hasStock.toString())
            intent.putExtra("rackID", "R001")
            intent.putExtra("productID", productId)
            startActivity(intent)
        }
        else if(hasStock == 1 && page.equals("2"))
        {
            val intent = Intent(this, StockOut::class.java)
            intent.putExtra("productID", productId)
            startActivity(intent)
        }
        else if(hasStock == 0 && page.equals("1"))
        {
            val intent = Intent(this, RackScan::class.java)
            intent.putExtra("hasStock", hasStock)
            intent.putExtra("productID", productId)
            startActivity(intent)
        }
        else if(hasStock == 0 && page.equals("2"))
        {
            Toast.makeText(this, "No this Product ID", Toast.LENGTH_LONG).show();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
