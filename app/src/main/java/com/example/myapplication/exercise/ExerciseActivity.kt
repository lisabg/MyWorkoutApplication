package com.example.myapplication.exercise

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DashboardActivity
import com.example.myapplication.R
import com.example.myapplication.entities.Exercise
import kotlinx.android.synthetic.main.exercise_main_layout.*
import kotlinx.android.synthetic.main.exercise_new_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*

class ExerciseActivity : AppCompatActivity() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var itemTouchHelperCallBack: ItemTouchHelper.SimpleCallback

    private lateinit var deleteIcon: Drawable
    private var swipeBackground: ColorDrawable = ColorDrawable(Color.parseColor("#FF0000"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exercise_main_layout)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.exercise_title)

        val db = ExerciseDataBaseHandler(this)
        val exerciseData = ArrayList<Exercise>()
        // deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_black_24dp)!!


        val data = db.readExerciseData()
        for (i in 0 until (data.size)) {
            exerciseData.add(data[i])
        }

        viewAdapter = ExerciseRecyclerViewAdapter(exerciseData, this@ExerciseActivity)
        viewManager = LinearLayoutManager(this)

        findViewById<RecyclerView>(R.id.recycler_view_exercises).apply {
            // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        addNewExerciseFunctionality(exerciseData, db)

        val itemTouchHelper = ItemTouchHelper(swipeFunctionality(db))
        itemTouchHelper.attachToRecyclerView(recycler_view_exercises)

    }

    private fun swipeFunctionality(db: ExerciseDataBaseHandler): ItemTouchHelper.SimpleCallback {

        itemTouchHelperCallBack =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    val exerciseName =
                        (viewAdapter as ExerciseRecyclerViewAdapter).removeExerciseItem(viewHolder, db)
                    db.deleteExerciseData(exerciseName)
                }


                //ICON NOT BEING DRAWN CORRECTLY
                /*override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2

                    if (dX > 0) {
                        swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        deleteIcon.setBounds(
                            itemView.left + iconMargin,
                            itemView.top + iconMargin,
                            itemView.right + iconMargin + deleteIcon.intrinsicWidth,
                            itemView.bottom - iconMargin)
                    }

                    swipeBackground.draw(c)
                    c.save()

                    if (dX > 0) c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    deleteIcon.draw(c)
                    c.restore()

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }*/
            }
        return itemTouchHelperCallBack
    }

    private fun addNewExerciseFunctionality(
        data: ArrayList<Exercise>,
        db: ExerciseDataBaseHandler
    ) {

        add_exercise_button.setOnClickListener {
            //inflate the dialog with custom view
            val mExerciseDialogView =
                LayoutInflater.from(this).inflate(R.layout.exercise_new_dialog, null)

            //AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mExerciseDialogView)
                .setTitle(R.string.exercise_dialog_box_title)
            val mAlertDialog = mBuilder.show()

            mExerciseDialogView.add_exercise_submit_button.setOnClickListener {
                if (!mExerciseDialogView.new_exercise_title_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_description_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_repetition_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_sets_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_weight_input.text.isBlank() &&
                    !mExerciseDialogView.new_exercise_goal_input.text.isBlank()
                ) {

                    mAlertDialog.dismiss()

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

                    db.insertExerciseData(exercise)
                    updateViewData(db, data)

                    Toast.makeText(this, "Exercise added", Toast.LENGTH_SHORT).show()

                } else Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            }

            mExerciseDialogView.add_exercise_cancel_button.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    private fun updateViewData(db: ExerciseDataBaseHandler, myPermData: ArrayList<Exercise>) {
        val data = db.readExerciseData()
        var added = 0

        for (i in 0 until (data.size)) {
            var duplicates = 0
            for (j in 0 until (myPermData.size)) {
                if (data[i].name == myPermData[j].name) {
                    duplicates++
                }
            }
            if (duplicates == 0) {
                myPermData.add(data[i])
                added++
            }
        }
        //update display
        findViewById<RecyclerView>(R.id.recycler_view_exercises).apply {
            adapter = ExerciseRecyclerViewAdapter(myPermData, this@ExerciseActivity)
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
