package com.example.myapplication.exercise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DashboardActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.new_exercise_dialog.view.*

class ExerciseActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)


        val myData = ArrayList<Exercise>()

        myData.add(
            Exercise(
                "Plank", "Place the palm of your hands on the floor centering your hands straight " +
                        "under your shoulders, place your feet so that your body is parallell to the ground and you " +
                        "are standing on your toes.", 30, 3
            )
        )
        myData.add(
            Exercise(
                "Push-ups", "Start in a plank-position with your hands in a wider stans" +
                        "than your shoulders. Lower yourself as far as possible and then push back up.",
                10, 3
            )
        )


        viewManager = LinearLayoutManager(this)

        findViewById<RecyclerView>(R.id.recycler_view_exercises).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = ExerciseAdapter(myData)
        }


        add_exercise_button.setOnClickListener {
            //inflate the dialog with custom view
            val mExerciseDialogView = LayoutInflater.from(this).inflate(R.layout.new_exercise_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mExerciseDialogView)
                .setTitle(R.string.exercise_dialog_box_title)
            val mAlertDialog = mBuilder.show()

            mExerciseDialogView.add_exercise_submit_button.setOnClickListener{
                mAlertDialog.dismiss()

                val title = mExerciseDialogView.new_exercise_title_input.text.toString()
                val description = mExerciseDialogView.new_exercise_description_input.text.toString()
                val repetitions = mExerciseDialogView.new_exercise_repetition_input.text.toString()
                val sets = mExerciseDialogView.new_exercise_sets_input.text.toString()

                //add input to data array for display
                myData.add(
                    Exercise(
                        title,
                        description,
                        repetitions.toLong(),
                        sets.toLong()
                    )
                )

                //update display
                findViewById<RecyclerView>(R.id.recycler_view_exercises).apply {
                    adapter = ExerciseAdapter(myData)
                }
            }

            mExerciseDialogView.add_exercise_cancel_button.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_dashboard_button -> {
                startActivity(Intent(this@ExerciseActivity, DashboardActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
