package com.task.moviedb.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.task.moviedb.R
import com.task.moviedb.cache.Cache
import com.task.moviedb.core.MovieApp

fun getCacheInstance(): Cache {
    if(MovieApp.cache == null){
        MovieApp.cache = Cache()
    }
    return MovieApp.cache!!
}

fun getImage(context: Activity,imageURL: String, imageView: ImageView) {
    try {
        if (!imageURL.isEmpty()) {
            Glide.with(context).load(imageURL).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.movie_icon)
        }
    } catch (e: Exception) {
        imageView.setImageResource(R.drawable.movie_icon)
        e.printStackTrace()
    }
}