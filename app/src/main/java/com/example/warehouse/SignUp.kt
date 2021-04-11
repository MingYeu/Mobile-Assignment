package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        findViewById<EditText>(R.id.txtIptUN).setText("")
        findViewById<EditText>(R.id.txtIptPwd).setText("")
        findViewById<EditText>(R.id.txtIptE).setText("")
        findViewById<EditText>(R.id.txtIptFN).setText("")

        val btnSignIn = findViewById<Button>(R.id.btnSignIn2)
        btnSignIn.setOnClickListener() {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

        //Save as admin (1)
        val btnCreateAccount1= findViewById<Button>(R.id.btnCreateAccount1)
        btnCreateAccount1.setOnClickListener() {

            clickSave1()

        }

        //Save as Staff (2)
        val btnCreateAccount2= findViewById<Button>(R.id.btnCreateAccount2)
        btnCreateAccount2.setOnClickListener() {

            clickSave2()

        }
    }

    private fun clickSave1() {
        val name = findViewById<EditText>(R.id.txtIptFN)
        val username = findViewById<EditText>(R.id.txtIptUN)
        val email = findViewById<EditText>(R.id.txtIptE)
        val password = findViewById<EditText>(R.id.txtIptPwd)

        if (name.text.isEmpty() || username.text.isEmpty() || email.text.isEmpty() || password.text.isEmpty()) {
            fillAllField()
        } else {
            saveAdmin()

            val intent = Intent(this, CreateAccSuccess::class.java)
            startActivity(intent)
        }
    }

    private fun clickSave2() {
        val name = findViewById<EditText>(R.id.txtIptFN)
        val username = findViewById<EditText>(R.id.txtIptUN)
        val email = findViewById<EditText>(R.id.txtIptE)
        val password = findViewById<EditText>(R.id.txtIptPwd)

        if (name.text.isEmpty() || username.text.isEmpty() || email.text.isEmpty() || password.text.isEmpty()) {
            fillAllField()
        } else {
            saveStaff()

            val intent = Intent(this, CreateAccSuccess::class.java)
            startActivity(intent)
        }
    }

    private fun fillAllField() {
        //Toast
//        val myToast = Toast.makeText(applicationContext,"Please fill in all fields!",Toast.LENGTH_LONG)
//        myToast.setGravity(Gravity.CENTER,0,0)
//        myToast.show()
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Please fill in all fields!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

    private fun saveStaff() {

        val name = findViewById<EditText>(R.id.txtIptFN).text.toString()
        val username = findViewById<EditText>(R.id.txtIptUN).text.toString()
        val email = findViewById<EditText>(R.id.txtIptE).text.toString()
        val password = findViewById<EditText>(R.id.txtIptPwd).text.toString()

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child("Staff")

        val addUser = User(name, username, email, password)
        myRef.child(username).setValue(addUser)
    }

    private fun saveAdmin() {

        val name = findViewById<EditText>(R.id.txtIptFN).text.toString()
        val username = findViewById<EditText>(R.id.txtIptUN).text.toString()
        val email = findViewById<EditText>(R.id.txtIptE).text.toString()
        val password = findViewById<EditText>(R.id.txtIptPwd).text.toString()

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child("Admin")

        val addUser = User(name, username, email, password)
        myRef.child(username).setValue(addUser)
    }

    override fun onBackPressed() {

    }

}