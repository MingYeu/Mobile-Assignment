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

class ChangePassword:AppCompatActivity() {

    lateinit var current:String
    lateinit var new:String
    lateinit var confirm:String
    lateinit var temp1:String
    lateinit var temp2:String
    lateinit var temp3:String
    lateinit var temp4:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        findViewById<EditText>(R.id.currentPass).setText("")
        findViewById<EditText>(R.id.newPass).setText("")
        findViewById<EditText>(R.id.confirmPass).setText("")

        //get from last activity
        val type = intent.getStringExtra("userType").toString()
        val userName = intent.getStringExtra("uname").toString()

        //val here
        val currentPassword = findViewById<EditText>(R.id.currentPass)
        val newPassword = findViewById<EditText>(R.id.newPass)
        val confirmPassword = findViewById<EditText>(R.id.confirmPass)
        val btnConfirmChange = findViewById<Button>(R.id.confirmChange)
        val btnCancelChange = findViewById<Button>(R.id.cancelChange)

        //read all from db
        val database1 = FirebaseDatabase.getInstance()
        val myRef1 = database1.getReference("User").child(type).child(userName)
        // Read from the database
        var getData = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                temp1 = dataSnapshot.child("username").getValue().toString()
                temp2 = dataSnapshot.child("email").getValue().toString()
                temp3 = dataSnapshot.child("password").getValue().toString()
                temp4 = dataSnapshot.child("name").getValue().toString()

            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        myRef1.addValueEventListener(getData)
        myRef1.addListenerForSingleValueEvent(getData)

        //Confirm Button
        btnConfirmChange.setOnClickListener(){

            val current = currentPassword.text.toString()
            val new = newPassword.text.toString()
            val confirm = confirmPassword.text.toString()

            //check empty fields
            if (current == null && new == null && confirm == null) {
                incorrectPassword()
            }else {
                //check current pass same with db pass
                if (current != null && new != null && confirm != null && temp3 != current) {
                    incorrectPassword()
                }else if (current != null && new != null && confirm != null && new != confirm) {
                    incorrectPassword()
                }else if (current != null && new != null && confirm != null && new == confirm && temp3 == current){
                    //update to db
                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("User").child(type)
                    val updatePass = User(temp4, temp1, temp2, confirm)
                    myRef.child(userName).setValue(updatePass)

                    val myToast = Toast.makeText(applicationContext,"Password successfully updated!", Toast.LENGTH_LONG).show()

                    findViewById<EditText>(R.id.currentPass).setText("")
                    findViewById<EditText>(R.id.newPass).setText("")
                    findViewById<EditText>(R.id.confirmPass).setText("")

                    val intent = Intent(this, MyProfile::class.java)
                    intent.putExtra("userType", type);
                    intent.putExtra("uname", userName);
                    startActivity(intent)
                }
            }
        }

        btnCancelChange.setOnClickListener(){
            onBackPressed()
        }

    }

//    private fun fillAllField() {
//        val alert = AlertDialog.Builder(this)
//        alert.setTitle("Error")
//        alert.setMessage("Please fill in all fields!")
//
//        alert.setPositiveButton("OK", null)
//        alert.show()
//    }

    private fun incorrectPassword() {
        findViewById<EditText>(R.id.currentPass).setText("")
        findViewById<EditText>(R.id.newPass).setText("")
        findViewById<EditText>(R.id.confirmPass).setText("")

        val alert = AlertDialog.Builder(this)
        alert.setTitle("Error")
        alert.setMessage("Password incorrect!")

        alert.setPositiveButton("OK", null)
        alert.show()
    }

}