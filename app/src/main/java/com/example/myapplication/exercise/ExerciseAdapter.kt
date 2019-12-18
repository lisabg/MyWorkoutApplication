package com.example.myapplication.exercise

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataBaseHandler
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class ExerciseAdapter(private val exerciseList: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.MyViewHolder>() {

    private var removedPosition : Int = 0
    private var removedItem: Exercise = Exercise()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_card_view_layout, parent, false)
        return MyViewHolder(view, exerciseList)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val exercise: Exercise = exerciseList[position]

        val name = exercise.name
        val repetitions = "Repetitions: " + exercise.repetition
        val sets = "Sets: " + exercise.sets

        holder.exerciseName.text = name
        holder.exerciseRepetitions.text = repetitions
        holder.exerciseSets.text = sets
    }

    fun removeExerciseItem(viewHolder: RecyclerView.ViewHolder, db: DataBaseHandler) : String{
        removedPosition = viewHolder.adapterPosition
        removedItem = exerciseList[viewHolder.adapterPosition]

        exerciseList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "${removedItem.name} deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
            exerciseList.add(removedPosition, removedItem)
            db.insertExerciseData(removedItem)
            notifyItemInserted(removedPosition)
        }.show()

        return removedItem.name
    }

    // Return the size of your data sett (invoked by the layout manager)
    override fun getItemCount() = exerciseList.size


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View, private val exerciseList: ArrayList<Exercise>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val exerciseName = itemView.findViewById(R.id.exercise_name_id) as TextView
        val exerciseRepetitions = itemView.findViewById(R.id.exercise_repetition_id) as TextView
        val exerciseSets = itemView.findViewById(R.id.exercise_sets_id) as TextView

        override fun onClick(p0: View?) {

            val exercise = findExercise(exerciseName.text.toString())
            if (exercise.id == -1) {
                Toast.makeText(itemView.context, "ERROR. Try again.", Toast.LENGTH_SHORT).show()
                return
            }

            val intent = Intent(itemView.context, ExerciseDetailsActivity::class.java)

            intent.putExtra("name", exercise.name)
            intent.putExtra("description", exercise.description)
            intent.putExtra("repetition", exerciseRepetitions.text.toString())
            intent.putExtra("sets", exerciseSets.text.toString())

            startActivity(itemView.context, intent, null)
        }

        private fun findExercise(name : String) : Exercise {

            for (e in exerciseList) {
                if (e.name == name) return e
            }
            return Exercise(-1, "", "", 0, 0)
        }

    }

}