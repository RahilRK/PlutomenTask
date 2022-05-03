package com.rahilkarim.plutomentask.ui.userlist

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

class UserListViewModel(private val repository: Repository,
                        private val globalClass: GlobalClass
): ViewModel() {

    var tag = "UserListViewModel"

    private val _liveData = MutableLiveData<List<UserModel>>()
    val liveData: LiveData<List<UserModel>>
        get() = _liveData

    init {
        repository.getUserList(_liveData)
    }
}