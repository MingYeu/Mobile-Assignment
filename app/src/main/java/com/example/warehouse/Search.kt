package com.example.warehouse

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ValueEventListener
import java.util.*
import android.util.Base64
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import org.w3c.dom.Text

class Search : AppCompatActivity(){

    var imageDisplay : Bitmap? = null
    lateinit var productId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)


        val btn = findViewById<Button>(R.id.btnEnterProduct)
        btn.setOnClickListener {
            val id = findViewById<TextView>(R.id.ettProductID)
            navigate(id.text.toString())
        }

        val btnScan = findViewById<Button>(R.id.btnQRScan)
        btnScan.setOnClickListener {
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null) {
                if(result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                } else {
                    navigate(result.contents)
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private fun navigate(productId: String)
    {
        if(productId.startsWith("S"))
        {
            val intent = Intent(this, DisplayStock::class.java)
            intent.putExtra("productID", productId)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Wrong Product ID", Toast.LENGTH_LONG).show()
        }
    }
}