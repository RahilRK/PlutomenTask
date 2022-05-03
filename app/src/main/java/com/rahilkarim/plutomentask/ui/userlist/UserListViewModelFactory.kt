package com.rahilkarim.plutomentask.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class UserListViewModelFactory(private val repository: Repository,
                               private val globalClass: GlobalClass
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(repository, globalClass) as T
    }
}