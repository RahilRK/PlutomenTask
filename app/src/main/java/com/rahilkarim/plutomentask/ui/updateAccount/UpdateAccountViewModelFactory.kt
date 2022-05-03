package com.rahilkarim.plutomentask.ui.updateAccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class UpdateAccountViewModelFactory(private val repository: Repository,
                                    private val globalClass: GlobalClass
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdateAccountViewModel(repository, globalClass) as T
    }
}