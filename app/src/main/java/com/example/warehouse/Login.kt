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

class LogIn : AppCompatActivity() {

    lateinit var username : String
    lateinit var tryPassword : String
    lateinit var checkPwd : String
    lateinit var checkUE : String
    lateinit var dbPwd : String
    lateinit var type : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //declare button and textInput
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val btnFgtPwd = findViewById<Button>(R.id.btnForgetPwd)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)
        val txtUE = findViewById<EditText>(R.id.txtUE)
        val txtPwd = findViewById<EditText>(R.id.txtPwd)

        findViewById<EditText>(R.id.txtUE).setText("")
        findViewById<EditText>(R.id.txtPwd).setText("")


        btnSignIn.setOnClickListener(){


            checkUE = txtUE.text.toString()
            checkPwd = txtPwd.text.toString()

            if (txtUE.text.isEmpty()||txtPwd.text.isEmpty()) {
                fillAllField()
            } else {
                checkAdminU()
            }
        }

        btnFgtPwd.setOnClickListener(){
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        btnSignUp.setOnClickListener(){
            val intent = Intent(this, Verify::class.java)
            startActivity(intent)
        }

    }

    private fun fillAllField() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Please provide Username and Password!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

    private fun checkAdminU(){
        //CHECK USERNAME
        //db location
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child("Admin").child(checkUE)
        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                tryPassword = dataSnapshot.child("password").getValue().toString()
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
        val myRef = database.getReference("User").child("Staff").child(checkUE)
        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                tryPassword = dataSnapshot.child("password").getValue().toString()
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
        if (checkPwd == tryPassword){
            type="Admin"
            allowLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }else if(checkPwd != tryPassword){
            checkStaffU()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }
    }
    private fun checkLogin2(){
        if (checkPwd == tryPassword){
            type="Staff"
            allowLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }else if(checkPwd != tryPassword){
            denyLogin()
//            val myToast = Toast.makeText(applicationContext,success, Toast.LENGTH_LONG).show()
        }
    }

    private  fun allowLogin(){
        val myToast = Toast.makeText(applicationContext,"Login Successful", Toast.LENGTH_LONG).show()
        val intent = Intent(this, Homepage::class.java)
        intent.putExtra("userType", type);
        intent.putExtra("uname", checkUE);
        startActivity(intent)
    }

    private  fun denyLogin(){
        findViewById<EditText>(R.id.txtPwd).setText("")
        val myToast = Toast.makeText(applicationContext,"Invalid username or password", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {

    }
}


////db location
//val database = FirebaseDatabase.getInstance()
//val myRef = database.getReference("User").child("Staff")
//// Read from the database
//myRef.child(checkUE).addValueEventListener(object : ValueEventListener {
//    override fun onDataChange(dataSnapshot: DataSnapshot) {
//        val dbPwd = dataSnapshot.child("password").getValue().toString()
//    }
//    override fun onCancelled(error: DatabaseError) {
//    }
//})
//if (checkPwd == dbPwd){
//    val intent = Intent(this, MyProfile::class.java)
//    startActivity(intent)
//}else{
//    val intent = Intent(this, ForgotPassword::class.java)
//    startActivity(intent)
//}