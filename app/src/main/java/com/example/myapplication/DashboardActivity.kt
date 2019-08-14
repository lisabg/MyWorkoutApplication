package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_dashboard)



        val goToExercisesBtn = findViewById<ImageButton>(R.id.dashboard_exercises_button)
        goToExercisesBtn.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ExerciseActivity::class.java))
        }

        val goToStretchingBtn = findViewById<ImageButton>(R.id.dashboard_stretching_button)
        goToStretchingBtn.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, StretchingActivity::class.java))
        }

        val goToConverterBtn = findViewById<ImageButton>(R.id.dashboard_converter_button)
        goToConverterBtn.setOnClickListener {
            startActivity(Intent(this@DashboardActivity, ConverterActivity::class.java))
        }
    }
}
