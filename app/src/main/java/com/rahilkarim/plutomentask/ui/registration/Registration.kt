package com.rahilkarim.plutomentask.ui.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.rahilkarim.plutomentask.databinding.ActivityRegistrationBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import java.util.regex.Pattern

class Registration : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityRegistrationBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: RegistrationViewModel

//    val toolbar : androidx.appcompat.widget.Toolbar get() = binding.toolbar
//    val profileImage : CircleImageView get() = binding.profileImage
    val firstNameEd : TextInputEditText get() = binding.firstNameEd
    val lastNameEd : TextInputEditText get() = binding.lastNameEd
    val emailIdEd : TextInputEditText get() = binding.emailIdEd
    val password : TextInputEditText get() = binding.password
    val registerbt : Button get() = binding.registerbt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, RegistrationViewModelFactory(repository, globalClass))
            .get(RegistrationViewModel::class.java)
    }

    private fun onClick() {

        registerbt.setOnClickListener {

            if(isValidate()) {

                val key: String = globalClass.firebaseDbRef().push().getKey()!!
                val userModel = UserModel(key,
                    firstNameEd.text.toString(),
                    lastNameEd.text.toString(),
                    emailIdEd.text.toString(),
                    password.text.toString(),
                )
                viewModel.registerUser(userModel)
                globalClass.toastlong("Register successfully")
            }
        }
    }

    private fun isValidate():Boolean {

        if(firstNameEd.text.isNullOrEmpty()) {
            globalClass.toastlong("First name should not be empty")
            return false
        }
        else if(lastNameEd.text.isNullOrEmpty()) {
            globalClass.toastlong("Last name should not be empty")
            return false
        }
        else if(emailIdEd.text!!.isEmpty() || !isValidEmail(emailIdEd.text.toString())) {
            globalClass.toastlong("Invalid Email Id")
            return false
        }
        else if(password.text!!.length <= 7) {
            globalClass.toastlong("Password should contain atleast 8 charcters")
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}