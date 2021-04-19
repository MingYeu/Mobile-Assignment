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

class CreateNewPassword : AppCompatActivity() {

    lateinit var pass1 : String
    lateinit var pass2 : String
    lateinit var username : String
    lateinit var type : String
    lateinit var temp1 : String
    lateinit var temp2 : String
    lateinit var temp3 : String
    lateinit var temp4 : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_password)
        username = intent.getStringExtra("username").toString()
        type = intent.getStringExtra("userType1").toString()

        findViewById<EditText>(R.id.textNewPassword).setText("")
        findViewById<EditText>(R.id.textConfirmPassword).setText("")

        val textNewPassword = findViewById<EditText>(R.id.textNewPassword)
        val textConfirmPassword = findViewById<EditText>(R.id.textConfirmPassword)
        val btnConfirm = findViewById<Button>(R.id.button4)

        btnConfirm.setOnClickListener(){
            pass1 = textNewPassword.text.toString()
            pass2 = textConfirmPassword.text.toString()

            if (textNewPassword.text.isEmpty()||textConfirmPassword.text.isEmpty()){
                fillAllField()
            }else {
                if (pass1 != pass2) {
                    pwdNotSame()
                } else if (pass1 == pass2) {
                    getAll()
                    updatePwd()
                }
            }
        }

    }

    private fun pwdNotSame() {
        findViewById<EditText>(R.id.textConfirmPassword).setText("")
        val myToast = Toast.makeText(applicationContext, "Password entered not match", Toast.LENGTH_LONG).show()
    }

    private fun getAll(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child(type).child(username)

        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                temp1 = dataSnapshot.child("name").getValue().toString()
                temp2 = dataSnapshot.child("username").getValue().toString()
                temp3 = dataSnapshot.child("email").getValue().toString()
                temp4 = dataSnapshot.child("password").getValue().toString()
//                        val myToast = Toast.makeText(applicationContext,tryPassword,Toast.LENGTH_LONG).show()


                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("User").child(type)

                val updatePwd = User(temp1, temp2, temp3, pass2)
                myRef.child(username).setValue(updatePwd)


            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)
    }

    private fun updatePwd(){

        val myToast = Toast.makeText(applicationContext, "Password successfully updated!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, LogIn::class.java)
        startActivity(intent)
    }

    private fun fillAllField() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Please fill in both field!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

    override fun onBackPressed() {

    }
}