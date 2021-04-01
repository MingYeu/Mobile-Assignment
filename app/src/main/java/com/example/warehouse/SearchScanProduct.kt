package com.example.warehouse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class SearchScanProduct :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_scan_product)

        val scanner = IntentIntegrator(this)
        scanner.initiateScan()

        val clickScan = findViewById<Button>(R.id.product_scan_search)
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
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
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
            val intent = Intent(this, DisplayStock::class.java)
            intent.putExtra("productID", productId)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Wrong Product Code", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}

