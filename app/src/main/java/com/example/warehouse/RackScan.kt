package com.example.warehouse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class RackScan : AppCompatActivity() {

    lateinit var prodID:String
    lateinit var hasStock: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rack_scan)

        val product = intent?.getStringExtra("productID")
        prodID = product.toString()

        val haveStock = intent?.getStringExtra("hasStock")
        hasStock = haveStock.toString()

        val button: Button = findViewById(R.id.rack_scan)
        button.setOnClickListener {
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
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();

                    val intent = Intent(this, StockIn::class.java)
                    intent.putExtra("productID", prodID)
                    intent.putExtra("rackID", result.contents)
                    intent.putExtra("hasStock", hasStock)
                    startActivity(intent)
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}