package com.rk.silvertouchapp.util

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.rahilkarim.plutomentask.model.UserModel

class Repository(private val globalClass: GlobalClass) {

    var tag = "Repository"

    fun registerUser(userModel: UserModel) {

        globalClass.firebaseDbRef().child(userModel.id).setValue(userModel)
    }
}