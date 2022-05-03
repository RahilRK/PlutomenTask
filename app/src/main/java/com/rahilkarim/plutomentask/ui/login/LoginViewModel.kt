package com.rahilkarim.plutomentask.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.plutomentask.model.UserModel
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class LoginViewModel(private val repository: Repository,
                     private val globalClass: GlobalClass
): ViewModel() {

    var tag = "LoginViewModel"

    private val _liveData = MutableLiveData<List<UserModel>>()
    val liveData: LiveData<List<UserModel>>
    get() = _liveData

    fun login() {
        repository.getUserList(_liveData)
    }

    fun registerUser(userModel: UserModel) {
        repository.registerUser(userModel)
    }
}