package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DetailsRecyclerViewAdapter(private val historyList: MutableList<Long>) :
    RecyclerView.Adapter<DetailsRecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.details_card_view_layout, parent, false)
        return MyViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data: Long = historyList[position]
        holder.data.text = data.toString()
    }


    override fun getItemCount() = historyList.size


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val data = itemView.findViewById(R.id.details_card_view_data) as TextView

    }

}