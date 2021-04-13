package com.example.warehouse

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class HistoryReport() : AppCompatActivity(){

    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_report)
        val date = Date()
        var strDate = dateFormat.format(date).toString()

        textview_date = findViewById(R.id.tvDateReport)
        button_date = findViewById(R.id.btnDate)

        textview_date!!.text = strDate
        var selectedDate = textview_date!!.text.toString()
        var ref = FirebaseDatabase.getInstance().getReference("Report").child("History").child(strDate)

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener

//        var enterDate = findViewById<TextView>(R.id.editTextDate)
//        var newDate = dateFormat.format(enterDate.text).toString()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textview_date!!.text = dateFormat.format(cal.getTime())
                strDate=dateFormat.format(cal.getTime()).toString()
                updateDate(textview_date!!.text.toString())

            }
        }


        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@HistoryReport,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
//                        updateDate(textview_date!!.text.toString())
            }

        })

/*
        val btnDate: Button =findViewById(R.id.btnDate)
        btnDate.setOnClickListener() {
            strDate=newDate
            ref.addValueEventListener(getData)
            ref.addListenerForSingleValueEvent(getData)

        }
*/

        updateDate(strDate)
//        ref.addValueEventListener(getData)
//        ref.addListenerForSingleValueEvent(getData)

    }

    private fun updateDate(date:String) {
        var ref = FirebaseDatabase.getInstance().getReference("Report").child("History").child(date)

        var getData = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapShot: DataSnapshot) {
                var sbDate = StringBuilder();
                var sbId = StringBuilder();
                var sbName = StringBuilder();
                var sbQty = StringBuilder();
                var sbAction = StringBuilder();

                //               if(snapShot.getValue().toString().equals(newDate)){

                for (c in snapShot.children) {
                    var date = c.child("Date").getValue()
                    sbDate.append("${date}\n")
                    var proId = c.child("Product Id").getValue()
                    sbId.append("${proId}\n")
                    var proName = c.child("Product Name").getValue()
                    sbName.append("${proName}\n")
                    var quantity = c.child("Quantity").getValue()
                    sbQty.append("${quantity}\n")
                    var action = c.child("Action").getValue()
                    sbAction.append("${action}\n")

//                }
                }
                val tvDateData: TextView = findViewById(R.id.tvDateData)
                tvDateData.setText(sbDate)
                val tvProductIdHisData: TextView = findViewById(R.id.tvProductIdHisData)
                tvProductIdHisData.setText(sbId)
                val tvProductNameHisData: TextView = findViewById(R.id.tvProductNameHisData)
                tvProductNameHisData.setText(sbName)
                val tvActionData: TextView = findViewById(R.id.tvActionData)
                tvActionData.setText(sbAction)
                val tvHisQtyData: TextView = findViewById(R.id.tvHisQtyData)
                tvHisQtyData.setText(sbQty)


            }
        }
//                ref = FirebaseDatabase.getInstance().getReference("Report").child("History").child(strDate)
        ref.addValueEventListener(getData)
        ref.addListenerForSingleValueEvent(getData)

    }

}