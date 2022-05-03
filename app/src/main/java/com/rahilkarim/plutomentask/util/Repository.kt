package com.rk.silvertouchapp.util

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.rahilkarim.plutomentask.model.UserModel

class Repository(private val globalClass: GlobalClass) {

    var tag = "Repository"

    fun getUserList(liveData: MutableLiveData<List<UserModel>>) {
        globalClass.firebaseDbRef().get().addOnSuccessListener { dataSnapshot ->

            if(dataSnapshot != null && dataSnapshot.exists()) {

                /*for (snapshot in dataSnapshot.children) {
                    val key = snapshot.key
                    val value = snapshot.value.toString()
                    globalClass.log(TAG,"key: $key")
                    globalClass.log(TAG,"value: $value")
                }*/

                /*for (snapshot in dataSnapshot.children) {
                    val userModel: UserModel? = snapshot.getValue(UserModel::class.java)
                    if(userModel!!.emailId.equals(emailId.text.toString(),ignoreCase = true) &&
                        userModel.password.equals(password.text.toString(),ignoreCase = true)) {
                        globalClass.toastshort("Logged in successfully")
                        return@addOnSuccessListener
                    }
                }*/

                val list: List<UserModel> = dataSnapshot.children.map { snapshot->
                    snapshot.getValue(UserModel::class.java)!!
                }

                liveData.postValue(list)
            }
            else {
                globalClass.toastlong("No data found")
            }

        }.addOnFailureListener { e->
            val error = Log.getStackTraceString(e)
            globalClass.log(tag, error)
            globalClass.toastlong("Error occured")
        }

    }

    fun registerUser(userModel: UserModel) {

        globalClass.firebaseDbRef().child(userModel.id).setValue(userModel)
    }

    fun getUserDetail(liveData: MutableLiveData<UserModel>) {
        val queryRef: Query = globalClass.firebaseDbRef().orderByChild("id").equalTo(globalClass.getString("id"))
        queryRef.addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (snapshot in dataSnapshot.children) {
                            val userModel: UserModel? = snapshot.getValue(UserModel::class.java)
                            liveData.postValue(userModel)
                        }
                    }
                    else {
                        globalClass.toastshort("No data found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }
}