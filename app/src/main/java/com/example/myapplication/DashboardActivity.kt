package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.converter.ConverterActivity
import com.example.myapplication.exercise.ExerciseActivity
import com.example.myapplication.stretch.StretchActivity
import kotlinx.android.synthetic.main.dashboard_layout.*


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.dashboard_layout)

        dashboard_exercises_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ExerciseActivity::class.java))
        }

        dashboard_stretching_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, StretchActivity::class.java))
        }

        dashboard_converter_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ConverterActivity::class.java))
        }
    }
}
