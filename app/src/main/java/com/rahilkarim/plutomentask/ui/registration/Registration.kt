package com.rahilkarim.plutomentask.ui.registration

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rahilkarim.plutomentask.R
import com.rahilkarim.plutomentask.databinding.ActivityRegistrationBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import de.hdodenhof.circleimageview.CircleImageView
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst
import java.util.regex.Pattern

class Registration : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityRegistrationBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: RegistrationViewModel

    val profileImage : CircleImageView get() = binding.profileImage
    val firstNameEd : TextInputEditText get() = binding.firstNameEd
    val lastNameEd : TextInputEditText get() = binding.lastNameEd
    val emailIdEd : TextInputEditText get() = binding.emailIdEd
    val password : TextInputEditText get() = binding.password
    val registerbt : Button get() = binding.registerbt

    var selectedimagesArrayList = java.util.ArrayList<Uri>()

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

        profileImage.setOnClickListener {
            requestStoragePermission()
        }

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
                onBackPressed()
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

    fun requestStoragePermission() {

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        openImagePicker()
                    }
                    if (report.deniedPermissionResponses.size > 0) {
                        globalClass.log(TAG, "Storage permission Denied")
                        globalClass.toastlong("Storage permission required to choose image")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            })
            .withErrorListener { error ->
                globalClass.log(TAG + "__requestStoragePermission", error.toString())
            }
            .onSameThread()
            .check()
    }

    fun openImagePicker() {
        selectedimagesArrayList.clear()
        FilePickerBuilder.instance
            .setMaxCount(1)
            .enableCameraSupport(true)
            .setSelectedFiles(selectedimagesArrayList)
            .setActivityTheme(R.style.LibAppTheme)
            .pickPhoto(activity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                selectedimagesArrayList.clear()
                data.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)
                    ?.let { selectedimagesArrayList.addAll(it) }

                globalClass.log(TAG, "selectedImages: ${selectedimagesArrayList.size}")
                if(selectedimagesArrayList.isNotEmpty()) {

                    profileImage.setImageURI(selectedimagesArrayList[0])
                    globalClass.setStringData("profileImage",selectedimagesArrayList[0].toString())
                }
                else {
                    globalClass.toastlong("Unable to load image")
                }
            }
        }
    }
}