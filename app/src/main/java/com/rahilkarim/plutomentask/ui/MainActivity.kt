package com.rahilkarim.plutomentask.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rahilkarim.plutomentask.R
import com.rahilkarim.plutomentask.model.UserModel

class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
        try {
            database = FirebaseDatabase.getInstance("https://plutomen-task-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")

            val key: String = database.child("Users").push().getKey()!!
            val userModel = UserModel(key,"fname","lname")

            database.child(key).setValue(userModel).addOnSuccessListener {

                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{ e->
                val error = Log.getStackTraceString(e)
                Log.d(TAG, "addOnFailureListener: $error")
            }
        }
        catch (e: Exception) {
            val error = Log.getStackTraceString(e)
            Log.d(TAG, "Exception: $error")
        }
*/
    }
}