package com.example.myapplication.exercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ExerciseDetailAdapter(private val exerciseList: ArrayList<Exercise>) :
    RecyclerView.Adapter<ExerciseDetailAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseName = itemView.findViewById(R.id.exercise_name_details) as TextView
        val exerciseDescription = itemView.findViewById(R.id.exercise_description_details) as TextView
        val exerciseRepetitions = itemView.findViewById(R.id.exercise_repetition_details) as TextView
        val exerciseSets = itemView.findViewById(R.id.exercise_sets_details) as TextView

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_card_view_layout, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val exercise: Exercise = exerciseList[position]

        val repetitions = "Repetitions: " + exercise.repetition
        val sets = "Sets: " + exercise.sets

        holder.exerciseName.text = exercise.name
        holder.exerciseDescription.text = exercise.description
        holder.exerciseRepetitions.text = repetitions
        holder.exerciseSets.text = sets

    }

    // Return the size of your data sett (invoked by the layout manager)
    override fun getItemCount() = exerciseList.size

}