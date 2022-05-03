package com.rahilkarim.plutomentask.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.rahilkarim.plutomentask.databinding.ActivityLoginBinding
import com.rahilkarim.plutomentask.ui.home.MainActivity
import com.rahilkarim.plutomentask.ui.registration.Registration
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import java.util.regex.Pattern


class Login : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityLoginBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: LoginViewModel

//    val toolbar : androidx.appcompat.widget.Toolbar get() = binding.toolbar
    val emailId : TextInputEditText get() = binding.emailId
    val password : TextInputEditText get() = binding.password
    val loginbt : MaterialButton get() = binding.loginbt
    val registrationbt : MaterialButton get() = binding.registrationbt

    var userFound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
        observeData()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, LoginViewModelFactory(repository, globalClass))
            .get(LoginViewModel::class.java)
    }

    private fun onClick() {

        loginbt.setOnClickListener {
            if(isValidate()) {
                viewModel.login()
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

    private fun observeData() {

        viewModel.liveData.observe(this) { list ->

            for(userModel in list) {
                if(userModel.emailId.equals(emailId.text.toString(),ignoreCase = true) &&
                    userModel.password.equals(password.text.toString(),ignoreCase = true)) {
                    userFound = true
                    globalClass.setStringData("id",userModel.id)
                    globalClass.setBooleanData("isLoggedIn",true)

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }

            if(userFound) {
                globalClass.toastshort("Logged in successfully")
                userFound = false
            }
            else {
                globalClass.toastshort("No data found")
            }
        }
    }
}