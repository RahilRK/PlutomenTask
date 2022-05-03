package com.rahilkarim.plutomentask.ui

import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.rahilkarim.plutomentask.databinding.ActivityLoginBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rahilkarim.plutomentask.ui.registration.Registration
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import java.lang.System.console
import java.lang.System.err
import java.util.regex.Pattern


class Login : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityLoginBinding
    lateinit var globalClass: GlobalClass

//    val toolbar : androidx.appcompat.widget.Toolbar get() = binding.toolbar
    val emailId : TextInputEditText get() = binding.emailId
    val password : TextInputEditText get() = binding.password
    val loginbt : MaterialButton get() = binding.loginbt
    val registrationbt : MaterialButton get() = binding.registrationbt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
    }

    private fun onClick() {

        loginbt.setOnClickListener {
            if(isValidate()) {
                val query: Query = globalClass.firebaseDbRef().orderByChild("emailId").equalTo(emailId.text.toString())
                query.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(dataSnapshot.exists()) {
                            globalClass.log(TAG,"ex")
                        }

                        globalClass.toastlong("Login successfully")
                    }

                    override fun onCancelled(error: DatabaseError) {

                        globalClass.log(TAG, error.toString())
                        globalClass.toastlong("Error occured")
                    }
                })
            }
        }

        registrationbt.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }

    private fun isValidate():Boolean {

        if(emailId.text!!.isEmpty() || !isValidEmail(emailId.text.toString())) {
            globalClass.toastlong("Invalid Email Id")
            emailId.requestFocus()
            return false
        }
        else if(password.text.isNullOrEmpty() && password.text!!.length < 3) {
            globalClass.toastlong("Password should be atleast contain 3 character")
            password.requestFocus()
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}