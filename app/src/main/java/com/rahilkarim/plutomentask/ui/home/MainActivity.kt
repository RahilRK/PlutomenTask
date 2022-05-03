package com.rahilkarim.plutomentask.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.rahilkarim.plutomentask.databinding.ActivityMainBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rahilkarim.plutomentask.ui.login.Login
import com.rahilkarim.plutomentask.ui.updateAccount.UpdateAccount
import com.rahilkarim.plutomentask.ui.updateAccount.UpdateAccountViewModel
import com.rahilkarim.plutomentask.ui.updateAccount.UpdateAccountViewModelFactory
import com.rahilkarim.plutomentask.ui.userlist.UserList
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private var activity = this

    lateinit var binding : ActivityMainBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: HomeViewModel

    val profileImage : CircleImageView get() = binding.profileImage
    val firstName : TextView get() = binding.firstName
    val lastName : TextView get() = binding.lastName
    val password : TextView get() = binding.password
    val logoutbt : Button get() = binding.logoutbt
    val deleteAcBt : Button get() = binding.deleteAcBt
    val updateAcBt : Button get() = binding.updateAcBt
    val userlistBt : Button get() = binding.userlistBt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        onClick()
        observeData()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository, globalClass))
            .get(HomeViewModel::class.java)
    }

    private fun onClick() {

        logoutbt.setOnClickListener {
            globalClass.setBooleanData("isLoggedIn",false)
            globalClass.toastlong("Logout successfully")

            val intent = Intent(this, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        deleteAcBt.setOnClickListener {

            val databaseReference = globalClass.firebaseDbRef()
                .child(globalClass.getString("id")).removeValue()
            databaseReference.addOnCompleteListener { task->

                if(task.isSuccessful) {
                    globalClass.setBooleanData("isLoggedIn",false)
                    globalClass.toastlong("User account deleted successfully")

                    val intent = Intent(activity, Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                else {
                    globalClass.toastlong("Unable to delete account")
                }
            }
        }

        updateAcBt.setOnClickListener {

            val intent = Intent(activity, UpdateAccount::class.java)
            startActivity(intent)
        }

        userlistBt.setOnClickListener {

            val intent = Intent(activity, UserList::class.java)
            startActivity(intent)
        }
    }

    private fun observeData() {

        viewModel.liveData.observe(this) { model ->

            firstName.text = "First name: ${model.firstName}"
            lastName.text = "Last name: ${model.lastName}"
            password.text = "Password: ${model.password}"

            try {
                profileImage.setImageURI(Uri.parse(globalClass.getString("profileImage")))
            }
            catch (e: Exception) {
                globalClass.log(TAG,Log.getStackTraceString(e))
            }
        }
    }
}