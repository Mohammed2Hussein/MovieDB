package com.task.moviedb.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.moviedb.R
import com.task.moviedb.adapter.DiscoverFragmentAdapter
import com.task.moviedb.interfaces.ServiceAPI
import com.task.moviedb.model.MovieDiscoverResponse
import com.task.moviedb.utils.Constants
import com.task.moviedb.utils.EndlessRecyclerViewScrollListener
import com.task.moviedb.utils.getCacheInstance
import com.task.moviedb.utils.getRetrofitInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover.*


class DiscoverFragment : Fragment() {
    val TAG = "DiscoverFragment"
    var page = 1

    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var discoverFragmentAdapter: DiscoverFragmentAdapter? = null
     var layoutManager: LinearLayoutManager? = null

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible && page == 1){
            callEndpoints()
        }
    }

    private fun callEndpoints() {
        val serviceAPI: ServiceAPI = getRetrofitInstance()!!.create(ServiceAPI::class.java)
        compositeDisposable?.add(serviceAPI.discoverMovies(Constants.API_KEY, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResults, this::handleError))
    }

    private fun handleResults(movieDiscoverResponse: MovieDiscoverResponse) {

        if(getCacheInstance().movieDiscoverResponse == null) {
            getCacheInstance().movieDiscoverResponse = movieDiscoverResponse
            initRecyclerView()
        }else{
            if(getCacheInstance().movieDiscoverResponse?.page != movieDiscoverResponse.page) {
                getCacheInstance().movieDiscoverResponse?.page = movieDiscoverResponse.page
                getCacheInstance().movieDiscoverResponse?.totalPages =
                    movieDiscoverResponse.totalPages
                getCacheInstance().movieDiscoverResponse?.totalResults =
                    movieDiscoverResponse.totalResults

                getCacheInstance().movieDiscoverResponse?.results?.addAll(movieDiscoverResponse.results!!)
            }
        }
        Log.d(
            TAG,
            "handleResults() -> totalPages: " + movieDiscoverResponse.totalPages
        )
        discoverFragmentAdapter?.notifyDataSetChanged()
        discoverFragmentProgressBar.visibility = View.GONE
    }

    fun initRecyclerView(){
        layoutManager = LinearLayoutManager(activity)
        discoverFragmentRecyclerView.layoutManager = layoutManager
        discoverFragmentAdapter = DiscoverFragmentAdapter()
        discoverFragmentRecyclerView.adapter = discoverFragmentAdapter

        recyclerViewAction()
    }

    private fun handleError(t: Throwable) {
        Log.d(TAG, "handleError() -> Error: " + t.localizedMessage)
    }

    fun recyclerViewAction(){
        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager!!) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                this@DiscoverFragment.page = getCacheInstance().movieDiscoverResponse?.page!! + 1
                if (page <= getCacheInstance().movieDiscoverResponse?.totalPages!!) {
                    callEndpoints()
                    discoverFragmentProgressBar.visibility = View.VISIBLE
                }
            }
        }

        // Add the Endless scroll listener to RecyclerView
        discoverFragmentRecyclerView.addOnScrollListener(scrollListener!!)
    }
}