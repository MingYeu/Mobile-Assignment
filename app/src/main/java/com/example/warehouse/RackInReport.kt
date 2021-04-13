package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RackInReport : AppCompatActivity() {
//    lateinit var rackId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rack_in_report)

        val spinnerProperty = findViewById<View>(R.id.ddlSelectRack) as Spinner

        val btnInvReport: Button = findViewById(R.id.btnInvReport)
        btnInvReport.setOnClickListener {
            val intent = Intent(this, InventoryReport::class.java)
            intent.putExtra("SelectedItem", spinnerProperty.selectedItem.toString())
            startActivity(intent)
        }
        val refInventory = FirebaseDatabase.getInstance().getReference("Report").child("Inventory")

        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                val propertyAddressList: MutableList<String> = ArrayList()
                for (addressSnapshot in dataSnapshot.children) {
                    val propertyAddress = addressSnapshot.child("Rack").getValue(String::class.java)
                    if (propertyAddress != null) {
                        propertyAddressList.add(propertyAddress)
                    }
                }
                val addressAdapter = ArrayAdapter(this@RackInReport, android.R.layout.simple_spinner_item, propertyAddressList)
                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProperty.adapter = addressAdapter
            }
        }



        refInventory.addValueEventListener(getData)
        refInventory.addListenerForSingleValueEvent(getData)
    }
}
