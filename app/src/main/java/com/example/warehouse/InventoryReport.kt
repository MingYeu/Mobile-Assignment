package com.example.warehouse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator

class InventoryReport : AppCompatActivity() {
    lateinit var totalQuantity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_report)
        val rack = intent.getStringExtra("SelectedItem").toString()

        val refInventory = FirebaseDatabase.getInstance().getReference("Report").child("Inventory").child(rack)
        val refStock = FirebaseDatabase.getInstance().getReference("Stock")
        val tvRackId: TextView = findViewById(R.id.tvRackId)
        tvRackId.setText(rack)

        val changeRack = findViewById<Button>(R.id.btnRack)
        changeRack.setOnClickListener(){
            val intent = Intent(this, RackInReport::class.java)
            startActivity(intent)
        }

        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapShot: DataSnapshot) {
                for (c in snapShot.children) {
                    totalQuantity = snapShot.child("totalQty").getValue().toString()

                    val tvTotalQty: TextView = findViewById(R.id.tvTotalQty)
                    tvTotalQty.setText(" Total Quantity   : $totalQuantity")
                }
            }
        }



/*
        var getStockData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapShot: DataSnapshot) {
                var sbId = StringBuilder();
                var sbName = StringBuilder();
                var sbQty = StringBuilder();



                for (c in snapShot.children) {
                    if (c.child("rack").getValue().toString().equals(rack)) {
                        var prodID = c.child("stockId").getValue().toString()
                        sbId.append("${prodID}\n")
                        var prodName = c.child("name").getValue().toString()
                        sbName.append("${prodName}\n")
                        var prodQuantity = c.child("quantity").getValue().toString()
                        sbQty.append("${prodQuantity}\n")

                    }
                }
                val tvProductIdData: TextView = findViewById(R.id.tvProductIdData)
                tvProductIdData.setText(sbId)
                val tvProductNameData: TextView = findViewById(R.id.tvProductNameData)
                tvProductNameData.setText(sbName)
                val tvInvQtyData: TextView = findViewById(R.id.tvInvQtyData)
                tvInvQtyData.setText(sbQty)


            }


        }
*/


        refInventory.addValueEventListener(getData)
        refInventory.addListenerForSingleValueEvent(getData)
//        refStock.addValueEventListener(getStockData)
//        refStock.addListenerForSingleValueEvent(getStockData)
        val viewpager2 = findViewById<ViewPager2>(R.id.vpInventory)
        val adapter = InvFragmentAdapter (supportFragmentManager,lifecycle,rack)

        viewpager2.adapter=adapter
    }
}