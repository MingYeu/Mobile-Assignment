package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ForgotPassword : AppCompatActivity() {

    lateinit var username:String
    lateinit var email:String
    lateinit var chckEmail:String
    lateinit var type:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val textUsername = findViewById<EditText>(R.id.textUsername)
        val textEmailAddress = findViewById<EditText>(R.id.textEmailAddress)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        val btnCancel = findViewById<Button>(R.id.btnCancel2)

        btnContinue.setOnClickListener(){
            username=textUsername.text.toString()
            email=textEmailAddress.text.toString()

            if (textUsername.text.isEmpty()||textEmailAddress.text.isEmpty()) {
                fillAllField()
            } else {
                checkAdminU()
            }
        }

        btnCancel.setOnClickListener(){
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
    }

    private fun fillAllField() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Please provide Username and Email!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

    private fun checkAdminU(){
        //CHECK USERNAME
        //db location
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child("Admin").child(username)
        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                chckEmail = dataSnapshot.child("email").getValue().toString()
//                val myToast = Toast.makeText(applicationContext,tryPassword,Toast.LENGTH_LONG).show()

                checkLogin()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)
    }

    private fun checkStaffU(){
        //CHECK USERNAME
        //db location
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child("Staff").child(username)
        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                chckEmail = dataSnapshot.child("email").getValue().toString()
//                        val myToast = Toast.makeText(applicationContext,tryPassword,Toast.LENGTH_LONG).show()

                checkLogin2()

            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)

    }

    private fun checkLogin(){
        if (email == chckEmail){
            type = "Admin"
            allowLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }else if(email != chckEmail){
            checkStaffU()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }
    }
    private fun checkLogin2(){
        if (email == chckEmail){
            type = "Staff"
            allowLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }else if(email != chckEmail){
            denyLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }
    }

    private  fun allowLogin(){
        val myToast = Toast.makeText(applicationContext,"Verified Successful", Toast.LENGTH_LONG).show()
        val intent = Intent(this, CreateNewPassword::class.java)
        intent.putExtra("username", username);
        intent.putExtra("userType1", type);
        startActivity(intent)
    }

    private  fun denyLogin(){
        findViewById<EditText>(R.id.textEmailAddress).setText("")
        val myToast = Toast.makeText(applicationContext,"Please try again", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
    }

}