package com.rahilkarim.plutomentask.ui.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahilkarim.plutomentask.model.UserModel
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class RegistrationViewModel(private val repository: Repository,
                            private val globalClass: GlobalClass
): ViewModel() {

    var tag = "RegistrationViewModel"

    fun registerUser(userModel: UserModel) {
        repository.registerUser(userModel)
    }
}