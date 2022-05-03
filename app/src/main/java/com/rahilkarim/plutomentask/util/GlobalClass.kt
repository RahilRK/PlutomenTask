package com.rk.silvertouchapp.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rahilkarim.plutomentask.R
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

public class GlobalClass private constructor(mcontext: Context) {

    private var context: Context = mcontext
    private var preferences: SharedPreferences = mcontext.getSharedPreferences(
        mcontext.getString(R.string.app_name),
        Context.MODE_PRIVATE
    )
    private lateinit var database: DatabaseReference

    companion object{
        @Volatile private var INSTANCE: GlobalClass? = null

        fun getInstance(context: Context): GlobalClass {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = GlobalClass(context)
                }
            }

            return INSTANCE!!
        }
    }

    //todo preferences
    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun getIntWithD(key: String, defValue: Int): Int {
        return preferences.getInt(key, defValue)
    }

    fun setIntData(key: String?, setvalue: Int) {
        preferences.edit().putInt(key, setvalue).apply()
    }

    fun getString(key: String): String {
        return preferences.getString(key, "")!!
    }

    fun getStringWithD(key: String, defValue: String): String {
        return preferences.getString(key, defValue)!!
    }

    fun setStringData(key: String?, setvalue: String) {
        preferences.edit().putString(key, setvalue).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun setBooleanData(key: String?, setvalue: Boolean) {
        preferences.edit().putBoolean(key, setvalue).apply()
    }

    fun clearAllPreference(){
        preferences.edit().clear().commit()
    }

    public fun log(tag: String,msg: String) {
        Log.e(tag,msg)
    }

    public fun toastshort(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    public fun toastlong(message: String) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    public fun showSnackBar(rootView : View, message: String) {
        val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
//        snack.setAction("Click Me") {
//            // TODO when you tap on "Click Me"
//        }
        snack.show()
    }

    public fun hideKeyboard(view: Activity) {
        val view = view.currentFocus
        if (view != null) {
            val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    public fun firebaseDbRef(): DatabaseReference {
        database =
            FirebaseDatabase.getInstance("https://plutomen-task-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users")
        return database
    }
}