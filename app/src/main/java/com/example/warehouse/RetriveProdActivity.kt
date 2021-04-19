package com.example.warehouse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.util.ArrayList

class RetriveProdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrive_prod)

        val categoryName = intent.getStringExtra("productId").toString()
        val stateName = intent.getStringExtra("stateName").toString()

        val prod_Id:TextView = findViewById(R.id.prodId)
        val prod_Name:TextView = findViewById(R.id.prodName)
        val prodQty:TextView = findViewById(R.id.prodQty)
        val rack_Id:TextView = findViewById(R.id.rackId)
        val prod_Price:TextView = findViewById(R.id.itemPrice)
        val prodSpinner: Spinner = findViewById(R.id.prodItem)

        val items= ArrayList<categoryItem>()
        val showName = ArrayList<String>()
        val database = FirebaseDatabase.getInstance()
        val trackProd = database.getReference("State").child(stateName).child(categoryName)

        var getItem = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//
                for (c in snapshot.children) {
                    var itemName = c.child("name").getValue().toString()
                    var itemPrice = c.child("price").getValue().toString().toDouble()
                    var quantity = c.child("quantity").getValue().toString().toInt()
                    var rack = c.child("rack").getValue().toString()
                    var id = c.child("stockId").getValue().toString()
                    items.add(categoryItem(itemName,itemPrice,quantity,rack,id))
                    showName.add(id)
                }


                if (prodSpinner!=null){
                    val adapter = ArrayAdapter(
                        this@RetriveProdActivity,
                        android.R.layout.simple_spinner_item,
                        showName)
                    prodSpinner.adapter = adapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        }

        trackProd.addValueEventListener(getItem)
        prodSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val currentItem = items[position]
                prod_Id.setText(currentItem.id)
                prod_Name.setText(currentItem.itemName)
                rack_Id.setText(currentItem.rack)
                prodQty.setText(currentItem.quantity.toString())
                prod_Price.setText("RM %.2f".format(currentItem.price))
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
}