package com.example.warehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyProfile : AppCompatActivity() {

    lateinit var name:String
    lateinit var uName:String
    lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        val type = intent.getStringExtra("userType")
        val userName = intent.getStringExtra("uname")
        //val myToast = Toast.makeText(applicationContext,type,Toast.LENGTH_LONG).show()
        val type1 = type.toString()
        val userName1 = userName.toString()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("User").child(type1).child(userName1)
        // Read from the database
        var getData = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                name = dataSnapshot.child("name").getValue().toString()
                uName = dataSnapshot.child("username").getValue().toString()
                email = dataSnapshot.child("email").getValue().toString()
//                        val myToast = Toast.makeText(applicationContext,tryPassword,Toast.LENGTH_LONG).show()

                findViewById<TextView>(R.id.tvName).setText(name)
                findViewById<TextView>(R.id.tvUName).setText(uName)
                findViewById<TextView>(R.id.tvEmail).setText(email)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef.addValueEventListener(getData)
        myRef.addListenerForSingleValueEvent(getData)


        findViewById<TextView>(R.id.tvType).setText(type)

        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)
        btnEditProfile.setOnClickListener(){
            val intent = Intent(this, EditProfile::class.java)
            intent.putExtra("userType", type);
            intent.putExtra("uname", userName);
            startActivity(intent)
        }

        val btnChangePass = findViewById<Button>(R.id.btnChangePass)
        btnChangePass.setOnClickListener(){
            val intent = Intent(this, ChangePassword::class.java)
            intent.putExtra("userType", type);
            intent.putExtra("uname", userName);
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

}