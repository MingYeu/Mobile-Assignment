package com.example.warehouse

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import java.io.ByteArrayOutputStream

class StockIn : AppCompatActivity(){

//    lateinit var spinner: Spinner
//    lateinit var productType : String
    lateinit var productId : String
    lateinit var rackID : String

    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_in)

        //Product ID
        productId = intent?.getStringExtra("productID").toString()
        val tv = findViewById<TextView>(R.id.tvProductID)
        tv.text = "Product id : $productId"

        //Rack ID
        rackID = intent?.getStringExtra("rackID").toString()
        val rack = findViewById<TextView>(R.id.RackID)
        rack.text = "Rack id : $rackID"

        //Drop Down List Product Type
//        spinner = findViewById<Spinner>(R.id.ddlproductType)
//        val spinnerOption = arrayOf("Product Type:", "Computer", "Book", "Phone")
//        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerOption)
//
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onNothingSelected(parent: AdapterView<*>?) {
////                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
//            }
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                productType = spinnerOption[position]
//            }
//        }

        //Saved Button
        val buttonSave: Button = findViewById(R.id.saved)
        buttonSave.setOnClickListener {
            save()
        }

        val buttonCancel: Button = findViewById(R.id.btnCancel)
        buttonCancel.setOnClickListener {
            Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun selectImage(view: View) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)
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
                startActivityForResult(intentToGallery, 2)
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
                        BitmapHelper.getInstance().bitmap = selectedBitmap

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

    private fun save() {
        val prodName = findViewById<EditText>(R.id.etProductName)
        val quant = findViewById<EditText>(R.id.etProductQuantity)
        val price = findViewById<EditText>(R.id.etProductPrice)

        val alert = AlertDialog.Builder(this)

        if(prodName.text.isEmpty() || quant.text.isEmpty() || price.text.isEmpty() || selectedBitmap == null)
        {
            alert.setTitle("Error")
            alert.setMessage("Please fill in all the details")

            alert.setPositiveButton("Alright", null)
            alert.show()
        }
        else {
            alert.setTitle("Save")
            alert.setMessage("Are you sure")

            alert.setNegativeButton("No") { dialogInterface: DialogInterface?, which: Int -> Toast.makeText(applicationContext, "Not Saved", Toast.LENGTH_LONG).show() }
            alert.setPositiveButton("Yes") { dialogInterface: DialogInterface?, which: Int -> store()}

            alert.show()


        }
    }

    fun store(){
        storeData()

        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun storeData(){
        val prodName = findViewById<EditText>(R.id.etProductName)
        val quant = findViewById<EditText>(R.id.etProductQuantity)
        val price = findViewById<EditText>(R.id.etProductPrice)

        //Convert Bitmap to String
        val by = ByteArrayOutputStream()
        selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, by)
        val b = by.toByteArray()
        val imgaeStore = Base64.encodeToString(b, Base64.DEFAULT)

        //firebase location
        val reff = FirebaseDatabase.getInstance().getReference().child("Stock");
        val stockId = reff.push().key

        val stock = Stock(productId, prodName.text.toString(), quant.text.toString(), price.text.toString(), imgaeStore, rackID)

        reff.child(productId).setValue(stock).addOnCompleteListener{
            Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {

    }
}