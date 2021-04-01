package com.example.warehouse

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.decodeByteArray
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import android.util.Base64
import android.widget.Toast

class DisplayStock : AppCompatActivity(){

    lateinit var productId : String
    var imageDisplay : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.display_stock)

        //Intend Value
        productId = intent?.getStringExtra("productID").toString()

        //Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock").child(productId)

        var getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.child("stockId").getValue() == null)
                {
                    noProduct()
                }
                else{
                    var image = snapshot.child("image").getValue().toString()
                    var prodName = snapshot.child("name").getValue().toString()
                    var prodPrice = snapshot.child("price").getValue().toString()
                    var prodQuantity = snapshot.child("quantity").getValue().toString()
                    var prodID = snapshot.child("stockId").getValue().toString()
                    var rackID = snapshot.child("rack").getValue().toString()

                    //Image
                    val imageBytes = Base64.decode(image, 0)
                    val imag = decodeByteArray(imageBytes, 0, imageBytes.size)

                    val img = findViewById<ImageView>(R.id.imageView)
                    img.setImageBitmap(imag)

                    //Information
                    val prodId = findViewById<TextView>(R.id.tvProductID)
                    prodId.text = "Product id   : $prodID"
                    val prodRack = findViewById<TextView>(R.id.RackID)
                    prodRack.text = "Rack id      : $rackID"
                    val prodQuan = findViewById<TextView>(R.id.etProductQuantity)
                    prodQuan.text = "Quantity     : $prodQuantity"
                    val prodPri = findViewById<TextView>(R.id.etProductPrice)
                    prodPri.text  = "Price        : $prodPrice"
                    val prodNam = findViewById<TextView>(R.id.etProductName)
                    prodNam.text  = "Product Name : $prodName"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)

        val btn = findViewById<Button>(R.id.btnBack)
        btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun noProduct()
    {
        Toast.makeText(applicationContext, "No this product", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}