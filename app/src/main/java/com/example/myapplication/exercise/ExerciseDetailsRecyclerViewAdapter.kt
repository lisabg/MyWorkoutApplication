package com.example.myapplication.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ExerciseDetailsRecyclerViewAdapter(private val historyList: ArrayList<Long>) :
    RecyclerView.Adapter<ExerciseDetailsRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.details_card_view_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val weight: Long = historyList[position]
        holder.weight.text = weight.toString()
    }


    override fun getItemCount() = historyList.size


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val weight = itemView.findViewById(R.id.details_card_view_weight) as TextView

    }

}