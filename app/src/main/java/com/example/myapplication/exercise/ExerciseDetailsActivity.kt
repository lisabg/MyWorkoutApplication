package com.example.myapplication.exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.entities.Exercise
import com.example.myapplication.entities.calculateProgress
import kotlinx.android.synthetic.main.details_name_description_layout.*
import kotlinx.android.synthetic.main.exercise_details_layout.*
import kotlinx.android.synthetic.main.exercise_new_dialog.view.*
import kotlinx.android.synthetic.main.details_progressbar_layout.*
import kotlinx.android.synthetic.main.toolbar.*


class ExerciseDetailsActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_details_layout)

        setSupportActionBar(toolbar)
        toolbar.title = "Exercise details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        editExerciseFunctionality()

    }

    private fun editExerciseFunctionality() {

        edit_exercise_button.setOnClickListener {

            val mExerciseDialogView =
                LayoutInflater.from(this).inflate(R.layout.exercise_new_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mExerciseDialogView)
                .setTitle(R.string.stretch_edit_dialog_box_title)
            val mAlertDialog = mBuilder.show()

            setEditableValues(mExerciseDialogView)

            val currentWeight = mExerciseDialogView.new_exercise_weight_input.text.toString()

                mExerciseDialogView.add_exercise_submit_button.setOnClickListener {
                if (!mExerciseDialogView.new_exercise_title_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_description_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_repetition_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_sets_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_weight_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_goal_input.text.isBlank()
                ) {

                    mAlertDialog.dismiss()
                    startActivity(Intent(this@ExerciseDetailsActivity, ExerciseActivity::class.java))

                    val db = ExerciseDataBaseHandler(this)

                    val id = intent.extras?.getString("id")!!.toInt()
                    val title = mExerciseDialogView.new_exercise_title_input.text.toString()
                    val description = mExerciseDialogView.new_exercise_description_input.text.toString()
                    val repetitions = mExerciseDialogView.new_exercise_repetition_input.text.toString()
                    val sets = mExerciseDialogView.new_exercise_sets_input.text.toString()
                    val weight = mExerciseDialogView.new_exercise_weight_input.text.toString()
                    val weightGoal = mExerciseDialogView.new_exercise_goal_input.text.toString()


                    //add input to data array for display
                    val exercise = Exercise(
                        title,
                        description,
                        repetitions.toLong(),
                        sets.toLong(),
                        weight.toLong(),
                        weightGoal.toLong()
                    )

                    exercise.id = id

                    if (currentWeight != weight) {
                        db.insertWeightHistory(id, currentWeight.toLong())
                    }

                    db.updateExerciseData(exercise)

                    Toast.makeText(this, "Exercise updated", Toast.LENGTH_SHORT).show()

                } else Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

            mExerciseDialogView.add_exercise_cancel_button.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

    }

    private fun setEditableValues(view : View) {
        view.new_exercise_title_input.setText(intent.extras?.getString("name"), TextView.BufferType.EDITABLE)
        view.new_exercise_description_input.setText(intent.extras?.getString("description"), TextView.BufferType.EDITABLE)
        view.new_exercise_repetition_input.setText(intent.extras?.getString("repetition"), TextView.BufferType.EDITABLE)
        view.new_exercise_sets_input.setText(intent.extras?.getString("sets"), TextView.BufferType.EDITABLE)
        view.new_exercise_weight_input.setText(intent.extras?.getString("weight"), TextView.BufferType.EDITABLE)
        view.new_exercise_goal_input.setText(intent.extras?.getString("goal"), TextView.BufferType.EDITABLE)
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()

        val id = intent.extras?.getString("id")!!.toInt()
        val name = intent.extras?.getString("name")
        val description = intent.extras?.getString("description")
        val repetitions = intent.extras?.getString("repetition")
        val sets = intent.extras?.getString("sets")
        val weight = intent.extras?.getString("weight")
        val goal = intent.extras?.getString("goal")
        val start = intent.extras?.getString("start")


        details_name.text = name
        details_description.text = description
        exercise_repetition_details.text = getString(R.string.repetition_text, repetitions!!.toInt())
        exercise_sets_details.text = getString(R.string.sets_text, sets!!.toInt())
        exercise_weight_details.text = getString(R.string.weight_text, weight!!.toInt())
        exercise_goal_details.text = getString(R.string.goal_text, goal!!.toInt())

        //fetchHistory(id)

        if (weight == start) {
            details_progress_bar.progress = 0
            details_progress_percentage.text = "0%"
        }
        else {
            val percentage = calculateProgress(start!!.toLong(), weight.toLong(), goal.toLong())
            details_progress_bar.progress = percentage
            details_progress_percentage.text = getString(R.string.percentage_text, percentage)
        }
    }

    private fun fetchHistory(id: Int) {

        val db = ExerciseDataBaseHandler(this)
        val history = db.readExerciseHistoryData(id)

        viewAdapter = ExerciseDetailsRecyclerViewAdapter(history)
        viewManager = LinearLayoutManager(this)

        findViewById<RecyclerView>(R.id.details_recycler_view).apply {
            // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

}
