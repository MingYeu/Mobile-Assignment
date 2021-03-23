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
import java.io.ByteArrayOutputStream

class StockOut : AppCompatActivity(){

    lateinit var productId : String
    lateinit var image : String
    lateinit var productName : String
    lateinit var productQuantity : String
    lateinit var productPrice : String
    lateinit var rackId : String
    var imageDisplay : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_out)

        //Intend Value
        productId = intent?.getStringExtra("productID").toString()

        //Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock").child(productId)

        var getData = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                image = snapshot.child("image").getValue().toString()
                productName = snapshot.child("name").getValue().toString()
                productPrice = snapshot.child("price").getValue().toString()
                productQuantity = snapshot.child("quantity").getValue().toString()
                productId = snapshot.child("stockId").getValue().toString()
                rackId = snapshot.child("rack").getValue().toString()

                //Image
                val imageBytes = Base64.decode(image, 0)
                val imag = decodeByteArray(imageBytes, 0, imageBytes.size)
                imageDisplay = imag

                val img = findViewById<ImageView>(R.id.imageView)
                img.setImageBitmap(imag)

                //Information
                val prodId = findViewById<TextView>(R.id.tvProductID)
                prodId.text = "Product id   : $productId"
                val prodRack = findViewById<TextView>(R.id.RackID)
                prodRack.text = "Rack id      : $rackId"
                val prodQuan = findViewById<TextView>(R.id.etProductQuantity)
                prodQuan.text = "Quantity     : $productQuantity"
                val prodPri = findViewById<TextView>(R.id.etProductPrice)
                prodPri.text  = "Price        : $productPrice"
                val prodNam = findViewById<TextView>(R.id.etProductName)
                prodNam.text  = "Product Name : $productName"
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)

        val btn = findViewById<Button>(R.id.btnStock_out)
        btn.setOnClickListener {
            Save_Stock_out()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun Save_Stock_out() {
        //Convert Bitmap to String
        val by = ByteArrayOutputStream()
        imageDisplay?.compress(Bitmap.CompressFormat.JPEG, 100, by)
        val b = by.toByteArray()
        val imgaeStore = Base64.encodeToString(b, Base64.DEFAULT)

        //firebase location
        val reff = FirebaseDatabase.getInstance().getReference().child("Stock");
        val stockId = reff.push().key

        var enterQuantity = findViewById<TextView>(R.id.ettNewQuant)
        var newQuant = Integer.parseInt(enterQuantity.text.toString())

        //Quantity
        var newQuantity = productQuantity.toInt() - newQuant

        val stock = Stock(productId, productName, newQuantity.toString(), productPrice, imgaeStore, rackId)

        reff.child(productId).setValue(stock).addOnCompleteListener{
            Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
        }
    }
}