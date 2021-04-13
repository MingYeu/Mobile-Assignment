package com.example.warehouse

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
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
import java.util.*


class StockIn : AppCompatActivity(){

    //    lateinit var spinner: Spinner
    //    lateinit var productType : String

    lateinit var productId : String
    lateinit var rackID : String

    lateinit var image : String
    lateinit var productName : String
    lateinit var productQuantity : String
    lateinit var productPrice : String
    lateinit var totalQuantity : String

    var imageDisplay : Bitmap? = null

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

//        val ref = FirebaseDatabase.getInstance().getReference("Report")
//        ref.setValue("History")

        //Have Stock?
        if(intent?.getStringExtra("hasStock").toString() == "1")
        {
            //Database
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference()

            var getData = object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    image = snapshot.child("Stock").child(productId).child("image").getValue().toString()
                    productName = snapshot.child("Stock").child(productId).child("name").getValue().toString()
                    productPrice = snapshot.child("Stock").child(productId).child("price").getValue().toString()
                    productQuantity = snapshot.child("Stock").child(productId).child("quantity").getValue().toString()
                    productId = snapshot.child("Stock").child(productId).child("stockId").getValue().toString()
                    rackID = snapshot.child("Stock").child(productId).child("rack").getValue().toString()
                    totalQuantity=snapshot.child("Report").child("Inventory").child(rackID).child("totalQty").getValue().toString()

                    //Image
                    val imageBytes = Base64.decode(image, 0)
                    selectedBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//                    imageDisplay = imag

                    val img = findViewById<ImageView>(R.id.imageView)
                    img.setImageBitmap(selectedBitmap)

                    //Information
                    val prodId = findViewById<TextView>(R.id.tvProductID)
                    prodId.text = "Product id   : $productId"
                    val prodRack = findViewById<TextView>(R.id.RackID)
                    prodRack.text = "Rack id      : $rackID"
                    val prodPri = findViewById<EditText>(R.id.etProductPrice)
                    prodPri.setText("Price   : $productPrice")
                    val prodNam = findViewById<EditText>(R.id.etProductName)
                    prodNam.setText(" Name   : $productName")

                    prodNam.isEnabled = false
                    prodPri.isEnabled = false

                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
/*
            val refInventory = FirebaseDatabase.getInstance().getReference("Report").child("Inventory").child(rackID)

            var getInvData=object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapShot: DataSnapshot) {
                    totalQuantity=snapShot.child("totalQty").getValue().toString()

                }
            }
*/


            myRef.addValueEventListener(getData)
            myRef.addListenerForSingleValueEvent(getData)

//            refInventory.addValueEventListener(getInvData)
//            refInventory.addListenerForSingleValueEvent(getInvData)
        }

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
    }

    private fun storeData(){
        val prodName = findViewById<EditText>(R.id.etProductName)
        val quant = findViewById<EditText>(R.id.etProductQuantity)
        val price = findViewById<EditText>(R.id.etProductPrice)

        //Quantity
        val updateQuantity = quant.text.toString().toInt() + productQuantity.toInt()
        //date
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        val strDate = dateFormat.format(date).toString()
        val action= "In"

        //Convert Bitmap to String
        val by = ByteArrayOutputStream()
        selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, by)
        val b = by.toByteArray()
        val imgaeStore = Base64.encodeToString(b, Base64.DEFAULT)

        //firebase location
        val reff = FirebaseDatabase.getInstance().getReference()
//        val stockId = reff.push().key
//        val refHistoryReport = FirebaseDatabase.getInstance().getReference("Report")

        val prodNam = findViewById<EditText>(R.id.etProductName)
        if(prodNam.isEnabled == false)
        {
            val stock = Stock(productId, productName, updateQuantity.toString(), productPrice, imgaeStore, rackID)
            reff.child("Stock").child(productId).setValue(stock).addOnCompleteListener{
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        else{
            val stock = Stock(productId, prodName.text.toString(), updateQuantity.toString(), price.text.toString(), imgaeStore, rackID)
            reff.child("Stock").child(productId).setValue(stock).addOnCompleteListener{
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        val ref = FirebaseDatabase.getInstance().getReference("Report").child("History").child(strDate)
        val pushedPostRef: DatabaseReference = ref.push()
        val postId = pushedPostRef.key

        val refReport=ref.child(postId.toString())
        refReport.child("Date").setValue(strDate)
        refReport.child("Product Id").setValue(productId)
        refReport.child("Product Name").setValue(productName)
        refReport.child("Action").setValue(action)
        refReport.child("Quantity").setValue(quant.text.toString())

        val refInventory = FirebaseDatabase.getInstance().getReference("Report").child("Inventory").child(rackID)
        val totalQty= (quant.text.toString().toInt()+totalQuantity.toInt()).toString()
        refInventory.child("totalQty").setValue(totalQty)
        refInventory.child("Rack").setValue(rackID)

    }

    override fun onBackPressed() {

    }
}