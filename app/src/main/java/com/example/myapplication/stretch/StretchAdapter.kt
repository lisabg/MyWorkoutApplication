package com.example.myapplication.stretch

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataBaseHandler
import com.example.myapplication.R
import com.example.myapplication.exercise.ExerciseDetailsActivity
import com.google.android.material.snackbar.Snackbar

class StretchAdapter(private val stretchList: ArrayList<Stretch>) :
    RecyclerView.Adapter<StretchAdapter.MyViewHolder>() {

    private var removedPosition : Int = 0
    private var removedItem : Stretch = Stretch()


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stretch_card_view_layout, parent, false)
        return MyViewHolder(view, stretchList)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val stretch: Stretch = stretchList[position]

        val time = "Seconds: " + stretch.seconds
        val sets = "Sets: " + stretch.sets

        holder.stretchName.text = stretch.name
        holder.stretchTime.text = time
        holder.stretchSets.text = sets

    }

    fun removeStretchItem(viewHolder: RecyclerView.ViewHolder, db: DataBaseHandler) : String{
        removedPosition = viewHolder.adapterPosition
        removedItem = stretchList[viewHolder.adapterPosition]

        stretchList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)

        Snackbar.make(viewHolder.itemView, "${removedItem.name} deleted", Snackbar.LENGTH_LONG).setAction("UNDO") {
            stretchList.add(removedPosition, removedItem)
            db.insertStretchData(removedItem)
            notifyItemInserted(removedPosition)
        }.show()

        return removedItem.name
    }

    // Return the size of your data sett (invoked by the layout manager)
    override fun getItemCount() = stretchList.size


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View, private val stretchList: ArrayList<Stretch>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        val stretchName = itemView.findViewById(R.id.stretch_name_id) as TextView
        val stretchTime = itemView.findViewById(R.id.stretch_time_id) as TextView
        val stretchSets = itemView.findViewById(R.id.stretch_sets_id) as TextView

        override fun onClick(p0: View?) {

            val stretch = findStretch(stretchName.text.toString())
            if (stretch.id == -1) {
                Toast.makeText(itemView.context, "ERROR. Try again.", Toast.LENGTH_SHORT).show()
                return
            }

            val intent = Intent(itemView.context, StretchDetailsActivity::class.java)

            intent.putExtra("id", stretch.id.toString())
            intent.putExtra("name", stretch.name)
            intent.putExtra("description", stretch.description)
            intent.putExtra("time", stretch.seconds.toString())
            intent.putExtra("sets", stretch.sets.toString())

            startActivity(itemView.context, intent, null)
        }

        private fun findStretch(name : String) : Stretch {

            for (e in stretchList) {
                if (e.name == name) return e
            }
            return Stretch(-1, "", "", 0, 0)
        }

    }
}

