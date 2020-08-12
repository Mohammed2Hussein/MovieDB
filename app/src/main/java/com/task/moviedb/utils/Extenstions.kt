package com.task.moviedb.utils

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
import com.task.moviedb.BuildConfig
import com.task.moviedb.R
import com.task.moviedb.cache.Cache
import com.task.moviedb.core.MovieApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

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

fun getRetrofitInstance(): Retrofit {
    if(MovieApp.retrofit == null) {
        // Init OkHttp Profiler
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }
        val client = builder.build()
        // Add OkHttp Profiler to retrofit
        MovieApp.retrofit = Retrofit.Builder()
            .client(client)
            ?.baseUrl(Constants.Base_URL)
            ?.addConverterFactory(MoshiConverterFactory.create())
            ?.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            ?.build()
    }
    return MovieApp.retrofit!!
}