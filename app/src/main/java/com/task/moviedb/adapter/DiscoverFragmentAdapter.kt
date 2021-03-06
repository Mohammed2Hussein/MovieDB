package com.task.moviedb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.task.moviedb.R
import com.task.moviedb.utils.Constants
import com.task.moviedb.utils.getCacheInstance
import com.task.moviedb.utils.getImage

class DiscoverFragmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            return getCacheInstance().movieDiscoverResponse?.results?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var imgMoviePoster = holder.itemView.findViewById<ImageView>(R.id.imgMoviePoster)
        var txtMovieName = holder.itemView.findViewById<TextView>(R.id.txtMovieName)
        var txtMovieReleaseDate = holder.itemView.findViewById<TextView>(R.id.txtMovieReleaseDate)
        var txtOverview = holder.itemView.findViewById<TextView>(R.id.txtOverview)

        txtMovieName.setText(getCacheInstance().movieDiscoverResponse?.results!![position]?.title)
        txtMovieReleaseDate.setText(getCacheInstance().movieDiscoverResponse?.results!![position]?.releaseDate)
        txtOverview.setText(getCacheInstance().movieDiscoverResponse?.results!![position]?.overview)
        getImage(holder.itemView.context, Constants.IMAGE_URL + getCacheInstance().movieDiscoverResponse?.results!![position]?.posterPath, imgMoviePoster)
    }

    internal inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

}