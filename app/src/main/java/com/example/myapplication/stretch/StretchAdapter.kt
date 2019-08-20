package com.example.myapplication.stretch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class StretchAdapter(private val stretchList: ArrayList<Stretch>) :
    RecyclerView.Adapter<StretchAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stretchName = itemView.findViewById(R.id.stretch_name_id) as TextView
        //val stretchDescription = itemView.findViewById(R.id.stretch_description_id) as TextView
        val stretchTime = itemView.findViewById(R.id.stretch_time_id) as TextView
        val stretchSets = itemView.findViewById(R.id.stretch_sets_id) as TextView

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stretch_card_view_layout, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val stretch: Stretch = stretchList[position]

        val time = "Seconds: " + stretch.time
        val sets = "Sets: " + stretch.sets

        holder.stretchName.text = stretch.name
        //holder.stretchDescription.text = stretch.description
        holder.stretchTime.text = time
        holder.stretchSets.text = sets

    }

    // Return the size of your data sett (invoked by the layout manager)
    override fun getItemCount() = stretchList.size
}

/*
fun refreshDataset() {
    mDataset = parseItems(mAppCtx)
    notifyDataSetChanged()
}
*/
