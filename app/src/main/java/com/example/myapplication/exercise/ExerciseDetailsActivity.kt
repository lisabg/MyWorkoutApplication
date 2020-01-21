package com.example.myapplication.exercise

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.DataBaseHandler
import com.example.myapplication.R
import kotlinx.android.synthetic.main.exercise_details_layout.*
import kotlinx.android.synthetic.main.exercise_new_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*


class ExerciseDetailsActivity : AppCompatActivity() {

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

            mExerciseDialogView.add_exercise_submit_button.setOnClickListener {
                if (!mExerciseDialogView.new_exercise_title_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_description_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_repetition_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_sets_input.text.isBlank()
                ) {

                    mAlertDialog.dismiss()
                    startActivity(
                        Intent(
                            this@ExerciseDetailsActivity,
                            ExerciseActivity::class.java
                        )
                    )

                    val db = DataBaseHandler(this)

                    val id = intent.extras?.getString("id")!!.toInt()
                    val title = mExerciseDialogView.new_exercise_title_input.text.toString()
                    val description = mExerciseDialogView.new_exercise_description_input.text.toString()
                    val repetitions = mExerciseDialogView.new_exercise_repetition_input.text.toString()
                    val sets = mExerciseDialogView.new_exercise_sets_input.text.toString()


                    //add input to data array for display
                    val exercise = Exercise(
                        id,
                        title,
                        description,
                        repetitions.toLong(),
                        sets.toLong()
                    )

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
