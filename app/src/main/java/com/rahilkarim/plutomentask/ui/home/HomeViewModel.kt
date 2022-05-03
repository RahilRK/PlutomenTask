package com.rahilkarim.plutomentask.ui.home

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

class HomeViewModel(private val repository: Repository,
                    private val globalClass: GlobalClass
): ViewModel() {

    var tag = "HomeViewModel"

    private val _liveData = MutableLiveData<UserModel>()
    val liveData: LiveData<UserModel>
        get() = _liveData

    init {
        repository.getUserDetail(_liveData)
    }
}