package com.task.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.moviedb.R

class SearchFragmentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_single_item_layout, parent, false)
        return ViewHolder(view)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        /*var lloHomeScreenSingleBellRow = holder.itemView.findViewById<LinearLayout>(R.id.lloHomeScreenSingleBellRow)
        var txtSingleBellRowName = holder.itemView.findViewById<TextView>(R.id.txtSingleBellRowName)
        var imgSingleBellRowStatus = holder.itemView.findViewById<ImageView>(R.id.imgSingleBellRowStatus)*/

    }

    internal inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}