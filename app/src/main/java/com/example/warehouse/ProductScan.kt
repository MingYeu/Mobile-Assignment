package com.example.warehouse

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ProductScan : AppCompatActivity() {

    lateinit var page : String
    lateinit var productId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_scan)

        page = intent?.getStringExtra("stock_in_out").toString()

        val scanner = IntentIntegrator(this)
        scanner.initiateScan()

        val clickScan = findViewById<ImageButton>(R.id.imageButton_Product)
        clickScan.setOnClickListener(){
            val scanner = IntentIntegrator(this)
            scanner.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null) {
                if(result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_SHORT).show();
                    navigate(result.contents)
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private fun navigate(productId : String)
    {
        if(productId.startsWith("S"))
        {
            if(page.equals("1"))
            {
                val intent = Intent(this, CheckStock::class.java)
                intent.putExtra("page", page)
                intent.putExtra("productID", productId)
                startActivity(intent)
            }
            else if(page.equals("2"))
            {
                val intent = Intent(this, CheckStock::class.java)
                intent.putExtra("page", page)
                intent.putExtra("productID", productId)
                startActivity(intent)
            }

        }
        else{
            Toast.makeText(applicationContext, "Wrong Product Code", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}