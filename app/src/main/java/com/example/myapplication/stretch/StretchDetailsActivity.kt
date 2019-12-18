package com.example.myapplication.stretch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.stretch_details_layout.*

class StretchDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stretch_details_layout)


        close_stretch_details_button.setOnClickListener {
            startActivity(Intent(this@StretchDetailsActivity, StretchActivity::class.java))
        }

        edit_stretch_details_button.setOnClickListener {
            Toast.makeText(this, "clicked EDIT button", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onStart() {
        super.onStart()

        stretch_description_details.text = intent.extras?.getString("description")
        stretch_time_details.text = intent.extras?.getString("time")
        stretch_sets_details.text = intent.extras?.getString("sets")

    }
}
