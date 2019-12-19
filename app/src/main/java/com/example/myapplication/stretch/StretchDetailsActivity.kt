package com.example.myapplication.stretch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.stretch_details_layout.*
import kotlinx.android.synthetic.main.toolbar.*

class StretchDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stretch_details_layout)

        setSupportActionBar(toolbar)
        toolbar.title = "Stretch details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        close_stretch_details_button.setOnClickListener {

            startActivity(Intent(this@StretchDetailsActivity, StretchActivity::class.java))
        }

        edit_stretch_details_button.setOnClickListener {
            Toast.makeText(this, "clicked EDIT button", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()

        stretch_name_details.text = intent.extras?.getString("name")
        stretch_description_details.text = intent.extras?.getString("description")
        stretch_time_details.text = intent.extras?.getString("time")
        stretch_sets_details.text = intent.extras?.getString("sets")

    }
}
