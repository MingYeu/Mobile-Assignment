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

class EditProfile:AppCompatActivity() {

    lateinit var temp1:String       //(1) = username
    lateinit var temp2:String       //(2) = email
    lateinit var temp3:String       //(3) = password
    lateinit var temp4:String       //(4) = name        ***new4 is confirm password
    lateinit var push1:String
    lateinit var push2:String
    lateinit var push3:String
    lateinit var push4:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        //get from last activity
        val type = intent.getStringExtra("userType").toString()
        val userName = intent.getStringExtra("uname").toString()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child(type).child(userName)
        // Read from the database and fill username and email
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                temp1 = dataSnapshot.child("username").getValue().toString()
                temp2 = dataSnapshot.child("email").getValue().toString()
                temp3 = dataSnapshot.child("password").getValue().toString()
                temp4 = dataSnapshot.child("name").getValue().toString()
//                        val myToast = Toast.makeText(applicationContext,tryPassword,Toast.LENGTH_LONG).show()

                findViewById<EditText>(R.id.changeName).setText(temp1)
                findViewById<EditText>(R.id.changeEmail).setText(temp2)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)

        //val here
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnCancel = findViewById<Button>(R.id.btnCancel3)


        //Cancel Button
        btnCancel.setOnClickListener(){
            onBackPressed()
        }

        //Update Button
        btnUpdate.setOnClickListener(){
            val myToast = Toast.makeText(applicationContext,"Processing, please wait...",Toast.LENGTH_SHORT).show()

            val changeName = findViewById<EditText>(R.id.changeName)
            val changeEmail = findViewById<EditText>(R.id.changeEmail)

            val new1 = changeName.text.toString()
            val new2 = changeEmail.text.toString()

            //check password field complete or not
            if (new1 == null || new2 == null){
                fillAllField()
            }else {
                //update to db
                val database = FirebaseDatabase.getInstance()
                val myRef = database.getReference("User").child(type)
                val updateProfile = User(temp4, new1, new2, temp3)
                myRef.child(userName).setValue(updateProfile)

                val myToast2 = Toast.makeText(applicationContext, "Success, info updated", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MyProfile::class.java)
                intent.putExtra("userType", type);
                intent.putExtra("uname", userName);
                startActivity(intent)
            }
        }

    }

    private fun fillAllField() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Please make sure the Username and Email fields are not empty!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

}