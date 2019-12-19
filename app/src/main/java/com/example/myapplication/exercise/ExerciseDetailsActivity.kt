package com.example.myapplication.exercise

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.exercise_details_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class ExerciseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_details_layout)

        setSupportActionBar(toolbar)
        toolbar.title = "Exercise details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

    }


    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()

        exercise_name_details.text = intent.extras?.getString("name")
        exercise_description_details.text = intent.extras?.getString("description")
        exercise_repetition_details.text = intent.extras?.getString("repetition")
        exercise_sets_details.text = intent.extras?.getString("sets")

    }
}
