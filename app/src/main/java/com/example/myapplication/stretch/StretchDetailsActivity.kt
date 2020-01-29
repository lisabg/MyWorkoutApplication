package com.example.myapplication.stretch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import com.example.myapplication.entities.Stretch
import com.example.myapplication.entities.calculateProgress
import kotlinx.android.synthetic.main.stretch_details_layout.*
import kotlinx.android.synthetic.main.stretch_new_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*

class StretchDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stretch_details_layout)

        setSupportActionBar(toolbar)
        toolbar.title = "Stretch details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        editStretchFunctionality()

    }

    private fun editStretchFunctionality() {

        edit_stretch_button.setOnClickListener {

            val mStretchDialogView =
                LayoutInflater.from(this).inflate(R.layout.stretch_new_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mStretchDialogView)
                .setTitle(R.string.stretch_edit_dialog_box_title)
            val mAlertDialog = mBuilder.show()

            setEditableValues(mStretchDialogView)

            mStretchDialogView.add_stretch_submit_button.setOnClickListener {
                if (!mStretchDialogView.new_stretch_title_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_description_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_time_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_sets_input.text.isBlank() &&
                    !mStretchDialogView.new_stretch_goal_input.text.isBlank()
                ) {

                    mAlertDialog.dismiss()
                    startActivity(Intent(this@StretchDetailsActivity, StretchActivity::class.java))

                    val db = StretchDataBaseHandler(this)

                    val id = intent.extras?.getString("id")!!.toInt()
                    val title = mStretchDialogView.new_stretch_title_input.text.toString()
                    val description = mStretchDialogView.new_stretch_description_input.text.toString()
                    val seconds = mStretchDialogView.new_stretch_time_input.text.toString()
                    val sets = mStretchDialogView.new_stretch_sets_input.text.toString()
                    val goals = mStretchDialogView.new_stretch_goal_input.text.toString()

                    //add input to data array for display
                    val stretch = Stretch(
                        title,
                        description,
                        seconds.toLong(),
                        sets.toLong(),
                        goals.toLong()
                    )

                    stretch.id = id

                    db.updateStretchData(stretch)

                    Toast.makeText(this, "Stretch updated", Toast.LENGTH_SHORT).show()

                } else Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

            mStretchDialogView.add_stretch_cancel_button.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

    }

    private fun setEditableValues(view : View) {
        view.new_stretch_title_input.setText(intent.extras?.getString("name"), TextView.BufferType.EDITABLE)
        view.new_stretch_description_input.setText(intent.extras?.getString("description"), TextView.BufferType.EDITABLE)
        view.new_stretch_time_input.setText(intent.extras?.getString("seconds"), TextView.BufferType.EDITABLE)
        view.new_stretch_goal_input.setText(intent.extras?.getString("goal"), TextView.BufferType.EDITABLE)
        view.new_stretch_sets_input.setText(intent.extras?.getString("sets"), TextView.BufferType.EDITABLE)
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()

        val name = intent.extras?.getString("name")
        val description = intent.extras?.getString("description")
        val seconds = intent.extras?.getString("seconds")
        val sets = intent.extras?.getString("sets")
        val goal = intent.extras?.getString("goal")
        val start = intent.extras?.getString("start")

        stretch_name_details.text = name
        stretch_description_details.text = description
        stretch_seconds_details.text = getString(R.string.seconds_text, seconds!!.toInt())
        stretch_sets_details.text = getString(R.string.sets_text, sets!!.toInt())
        stretch_goal_details.text = getString(R.string.seconds_goal_text, goal!!.toInt())

        if (seconds == start) {
            stretch_details_progress_bar.progress = 0
            stretch_details_progress_percentage.text = "0%"
        }
        else {
            val percentage = calculateProgress(start!!.toLong(), seconds.toLong(), goal.toLong())
            stretch_details_progress_bar.progress = percentage
            stretch_details_progress_percentage.text = getString(R.string.percentage_text, percentage)
        }

    }

}
