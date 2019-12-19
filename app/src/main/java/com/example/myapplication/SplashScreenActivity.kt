package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.toolbar.*

class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut = 2500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen_layout)

        Handler().postDelayed({
            startActivity(Intent(this@SplashScreenActivity, DashboardActivity::class.java))
            finish()
        }, splashTimeOut)


    }
}
