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

class EditStock : AppCompatActivity(){

    lateinit var spinner: Spinner
    lateinit var rackType : String

    lateinit var image : String
    lateinit var productName : String
    lateinit var productQuantity : String
    lateinit var productPrice : String

    lateinit var productId : String
    lateinit var rackID : String

    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    var changePicture : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_stock)

        //Intend Value
        productId = intent?.getStringExtra("productID").toString()

        //Database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock").child(productId)

        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                image = snapshot.child("image").getValue().toString()
                productName = snapshot.child("name").getValue().toString()
                productPrice = snapshot.child("price").getValue().toString()
                productQuantity = snapshot.child("quantity").getValue().toString()
                productId = snapshot.child("stockId").getValue().toString()
                rackID = snapshot.child("rack").getValue().toString()

                //Image
                val imageBytes = Base64.decode(image, 0)
                val imag = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                val img = findViewById<ImageView>(R.id.imageView)
                img.setImageBitmap(imag)

                //Information
                val prodId = findViewById<TextView>(R.id.tvProductID)
                prodId.text = "Product id   : $productId"
                val prodRack = findViewById<TextView>(R.id.RackID)
                prodRack.text = "Rack I      : $rackID"
                val prodQuan = findViewById<TextView>(R.id.etProductQuantity)
                prodQuan.text = "Quantity     : $productQuantity"
                val prodPri = findViewById<EditText>(R.id.etProductPrice)
                prodPri.setText("$productPrice")
                val prodNam = findViewById<EditText>(R.id.etProductName)
                prodNam.setText("$productName")

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)

//        Drop Down List Rack Type
        spinner = findViewById<Spinner>(R.id.RackIDSpin)
        val spinnerOption = arrayOf("R001", "R002", "R003", "R004", "R005", "R006", "R007", "R008", "R009", "R010")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, spinnerOption)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rackType = spinnerOption[position]
            }
        }

        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            UpdateProduct()
        }

        val btnCancel = findViewById<Button>(R.id.btnCancelUpdate)
        btnCancel.setOnClickListener {
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
                        changePicture = true
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

    private fun UpdateProduct()
    {
        val prodName = findViewById<EditText>(R.id.etProductName)
        val price = findViewById<EditText>(R.id.etProductPrice)

        val alert = AlertDialog.Builder(this)

        if(prodName.text.isEmpty() || price.text.isEmpty())
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
            alert.setPositiveButton("Yes") { dialogInterface: DialogInterface?, which: Int -> store() }

            alert.show()
        }
    }

    fun store(){
        storeData()
    }

    private fun storeData() {
        val prodName = findViewById<EditText>(R.id.etProductName)
        val price = findViewById<EditText>(R.id.etProductPrice)

        if(changePicture == true)
        {
            //Convert Bitmap to String
            val by = ByteArrayOutputStream()
            selectedBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, by)
            val b = by.toByteArray()
            val imgaeStore = Base64.encodeToString(b, Base64.DEFAULT)

            val reff = FirebaseDatabase.getInstance().getReference()

            val stock = Stock(productId, prodName.text.toString(), productQuantity, price.text.toString(), imgaeStore, rackType)
            reff.child("Stock").child(productId).setValue(stock).addOnCompleteListener{
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
        else{
            val reff = FirebaseDatabase.getInstance().getReference()

            val stock = Stock(productId, prodName.text.toString(), productQuantity, price.text.toString(), image, rackType)
            reff.child("Stock").child(productId).setValue(stock).addOnCompleteListener{
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }
    }
}