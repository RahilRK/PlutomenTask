package com.rahilkarim.plutomentask.ui.updateAccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.rahilkarim.plutomentask.databinding.ActivityUpdateAccountBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rahilkarim.plutomentask.ui.home.MainActivity
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import java.util.regex.Pattern

class UpdateAccount : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityUpdateAccountBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: UpdateAccountViewModel

    //    val profileImage : CircleImageView get() = binding.profileImage
    val firstNameEd : TextInputEditText get() = binding.firstNameEd
    val lastNameEd : TextInputEditText get() = binding.lastNameEd
    val emailIdEd : TextInputEditText get() = binding.emailIdEd
    val password : TextInputEditText get() = binding.password
    val updateacbt : Button get() = binding.updateacbt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
        observeData()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, UpdateAccountViewModelFactory(repository, globalClass))
            .get(UpdateAccountViewModel::class.java)
    }

    private fun onClick() {

        updateacbt.setOnClickListener {

            if(isValidate()) {

                val userModel = UserModel(globalClass.getString("id"),
                    firstNameEd.text.toString(),
                    lastNameEd.text.toString(),
                    emailIdEd.text.toString(),
                    password.text.toString(),
                )
                viewModel.updateAccount(userModel)
                globalClass.toastlong("Updated successfully")
                onBackPressed()
            }
        }
    }

    private fun observeData() {

        viewModel.liveData.observe(this) { model ->

            firstNameEd.setText(model.firstName)
            lastNameEd.setText(model.lastName)
            emailIdEd.setText(model.emailId)
            password.setText(model.password)
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