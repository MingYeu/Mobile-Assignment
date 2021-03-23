package com.example.warehouse

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ProductScan : AppCompatActivity() {

    lateinit var page : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_scan)

        page = intent?.getStringExtra("stock_in_out").toString()

        val scanner = IntentIntegrator(this)
        scanner.initiateScan()
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
            if(page.equals("1"))
            {
                val intent = Intent(this, RackScan::class.java)
                intent.putExtra("productID", productId)
                startActivity(intent)
            }
            else if(page.equals("2"))
            {
                val intent = Intent(this, StockOut::class.java)
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