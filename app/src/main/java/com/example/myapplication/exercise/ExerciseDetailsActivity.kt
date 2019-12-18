package com.example.myapplication.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.R
import kotlinx.android.synthetic.main.exercise_details_layout.*

class ExerciseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_details_layout)


        close_exercise_details_button.setOnClickListener {
            startActivity(Intent(this@ExerciseDetailsActivity, ExerciseActivity::class.java))
        }

        edit_exercise_details_button.setOnClickListener {
            Toast.makeText(this, "clicked EDIT button", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onStart() {
        super.onStart()

        exercise_name_details.text = intent.extras?.getString("name")
        exercise_description_details.text = intent.extras?.getString("description")
        exercise_repetition_details.text = intent.extras?.getString("repetition")
        exercise_sets_details.text = intent.extras?.getString("sets")

    }
}
