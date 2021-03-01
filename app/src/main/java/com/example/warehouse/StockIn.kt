package com.example.warehouse

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class StockIn : AppCompatActivity(){

    lateinit var spinner: Spinner
    lateinit var productType : String
    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_in)

        //Product ID
        val product = intent?.getStringExtra("productID")
        val tv = findViewById<TextView>(R.id.tvProductID)
        tv.setText(product)

        val rackID = intent?.getStringExtra("rackID")
        val rack = findViewById<TextView>(R.id.RackID)
        rack.setText(rackID)

        //Drop Down List Product Type
        spinner = findViewById<Spinner>(R.id.ddlproductType)
        val spinnerOption = arrayOf("Computer", "Book", "Phone")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerOption)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                productType = spinnerOption[position]
            }
        }
    }

    fun selectImage(view: View) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery,2)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == 1) {

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val img = findViewById<ImageView>(R.id.imageView)
        if ( requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPicture = data.data

            try {

                if (selectedPicture != null) {

                    if (Build.VERSION.SDK_INT >= 28) {
                        val source =
                                ImageDecoder.createSource(this.contentResolver, selectedPicture!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        img.setImageBitmap(selectedBitmap)

                    } else {
                        selectedBitmap =
                                MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPicture)
                        img.setImageBitmap(selectedBitmap)
                    }
                }
            } catch (e: Exception) {

            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun save(view: View) {

        val alert = AlertDialog.Builder(this)

        alert.setTitle("Save")
        alert.setMessage("Are you sure")

        alert.setNegativeButton("No") { dialogInterface: DialogInterface?, which: Int ->  Toast.makeText(applicationContext, "Not Saved", Toast.LENGTH_LONG).show()}
        alert.setPositiveButton("Yes") { dialogInterface: DialogInterface?, which: Int ->  Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()}

        alert.show()
    }
}