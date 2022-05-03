package com.rahilkarim.plutomentask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rahilkarim.plutomentask.databinding.ActivitySplashBinding
import com.rahilkarim.plutomentask.ui.home.MainActivity
import com.rahilkarim.plutomentask.ui.login.Login
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass

class Splash : AppCompatActivity() {

    var TAG = this.javaClass.simpleName
    private var activity = this

    lateinit var binding : ActivitySplashBinding
    lateinit var globalClass: GlobalClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        init()
        nextPage()
    }

    private fun init() {
        globalClass = (activity.application as Application).globalClass
    }

    private fun nextPage() {

        Handler(Looper.myLooper()!!).postDelayed(
            {
                if(globalClass.getBoolean("isLoggedIn")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {

                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            },1000L)
    }
}