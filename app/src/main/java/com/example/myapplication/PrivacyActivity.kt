package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*



class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.privacy_layout)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.privacy_policy_title_text)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }
}
