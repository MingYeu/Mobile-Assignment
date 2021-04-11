package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Verify : AppCompatActivity(){

    lateinit var checkAdmUE:String
    lateinit var checkAdmPwd:String
    lateinit var admPassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify)

        val btnCont = findViewById<Button>(R.id.btnCont)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val admUE = findViewById<EditText>(R.id.admUE)
        val admPwd = findViewById<EditText>(R.id.admPwd)

        findViewById<EditText>(R.id.admUE).setText("")
        findViewById<EditText>(R.id.admPwd).setText("")

        btnCont.setOnClickListener() {
            checkAdmUE = admUE.text.toString()
            checkAdmPwd = admPwd.text.toString()

            if (admUE.text.isEmpty()|| admPwd.text.isEmpty()) {
                val myToast = Toast.makeText(applicationContext,"Please fill in Username and Password!",Toast.LENGTH_LONG).show()
            } else {
                //db location
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("User").child("Admin").child(checkAdmUE)
                // Read from the database
                var getData = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        admPassword = dataSnapshot.child("password").getValue().toString()
                        checkAdmLogin()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        val myToast =Toast.makeText(applicationContext, "Database Connection Failed", Toast.LENGTH_LONG).show()
                    }
                }
                myRef.addValueEventListener(getData)
                myRef.addListenerForSingleValueEvent(getData)
            }
        }

        btnCancel.setOnClickListener(){
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }
    }


    private fun checkAdmLogin() {
        if (checkAdmPwd == admPassword){
//            val myToast = Toast.makeText(applicationContext,"Login As Admin Successful", Toast.LENGTH_LONG).show()
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }else{
            val myToast = Toast.makeText(applicationContext,"Login Failed", Toast.LENGTH_LONG).show()
        }
    }


}