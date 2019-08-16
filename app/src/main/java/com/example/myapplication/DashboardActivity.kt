package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.converter.ConverterActivity
import com.example.myapplication.exercise.ExerciseActivity
import com.example.myapplication.stretching.StretchingActivity
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_dashboard)

        dashboard_exercises_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ExerciseActivity::class.java))
        }

        dashboard_stretching_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, StretchingActivity::class.java))
        }

        dashboard_converter_button.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ConverterActivity::class.java))
        }
    }
}
