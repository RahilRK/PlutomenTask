package com.rahilkarim.plutomentask.ui.userlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.plutomentask.R
import com.rahilkarim.plutomentask.databinding.ActivityRegistrationBinding
import com.rahilkarim.plutomentask.databinding.ActivityUserListBinding
import com.rahilkarim.plutomentask.model.UserModel
import com.rahilkarim.plutomentask.ui.registration.RegistrationViewModel
import com.rahilkarim.plutomentask.ui.registration.RegistrationViewModelFactory
import com.rahilkarim.skpust.ui.BusinessDetailFrag.UserListAdapter
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class UserList : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivityUserListBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: UserListViewModel

    val recyclerView : RecyclerView get() = binding.recyclerView

    var arrayList = listOf<UserModel>()
    lateinit var adapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        observeData()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
        repository = (activity.application as Application).repository
        viewModel = ViewModelProvider(this, UserListViewModelFactory(repository, globalClass))
            .get(UserListViewModel::class.java)
    }

    private fun observeData() {

        viewModel.liveData.observe(this) { list ->
            arrayList = list

            if(arrayList.isNotEmpty()) {
                setAdapter()
            }
            else {
                globalClass.toastlong("No user found")
            }
        }
    }

    private fun setAdapter() {

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        adapter = UserListAdapter(activity, arrayList, object : UserListAdapter.contactListAdapterOnClick {

            override fun sendPush(pos: Int, model: UserModel) {

            }
        })

        recyclerView.adapter = adapter
    }
}