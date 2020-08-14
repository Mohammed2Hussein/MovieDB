package com.task.moviedb.view.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.task.moviedb.R
import com.task.moviedb.adapter.SearchFragmentAdapter
import com.task.moviedb.interfaces.ServiceAPI
import com.task.moviedb.model.MovieSearchResponse
import com.task.moviedb.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    val TAG = "SearchFragment"
    var dialog: Dialog? = null
    var page = 1

    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    var searchFragmentAdapter: SearchFragmentAdapter? = null
    var layoutManager: LinearLayoutManager? = null

    private var compositeDisposable: CompositeDisposable? = null

    var movieYear = 0
    var isContainsAdult = false
    var movieLang = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        if(menuVisible && page == 1){
            callEndpoints()
            viewsAction()
        }
    }

    private fun callEndpoints() {
        val serviceAPI: ServiceAPI = getRetrofitInstance()!!.create(ServiceAPI::class.java)
        compositeDisposable?.add(serviceAPI.searchMovies(Constants.API_KEY, page, movieLang, etxtSearchMovie.text.toString(), isContainsAdult, movieYear)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResults, this::handleError))
    }

    private fun handleResults(movieSearchResponse: MovieSearchResponse) {
        if(getCacheInstance().movieSearchResponse == null) {
            getCacheInstance().movieSearchResponse = movieSearchResponse
            initRecyclerView()
        }else{
            if(getCacheInstance().movieSearchResponse?.page != movieSearchResponse.page) {
                getCacheInstance().movieSearchResponse?.page = movieSearchResponse.page
                getCacheInstance().movieSearchResponse?.totalPages =
                    movieSearchResponse.totalPages
                getCacheInstance().movieSearchResponse?.totalResults =
                    movieSearchResponse.totalResults

                getCacheInstance().movieSearchResponse?.results?.addAll(movieSearchResponse.results!!)
            }
        }
        Log.d(
            TAG,
            "handleResults() -> totalPages: " + movieSearchResponse.totalPages
        )
        searchFragmentAdapter?.notifyDataSetChanged()
        searchFragmentProgressBar2.visibility = View.GONE
    }

    fun initRecyclerView(){
        layoutManager = LinearLayoutManager(activity)
        searchFragmentRecyclerView.layoutManager = layoutManager
        searchFragmentAdapter = SearchFragmentAdapter()
        searchFragmentRecyclerView.adapter = searchFragmentAdapter

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
                this@SearchFragment.page = getCacheInstance().movieSearchResponse?.page!! + 1
                if (page <= getCacheInstance().movieSearchResponse?.totalPages!!) {
                    callEndpoints()
                    searchFragmentProgressBar2.visibility = View.VISIBLE
                }
            }
        }

        // Add the Endless scroll listener to RecyclerView
        searchFragmentRecyclerView.addOnScrollListener(scrollListener!!)
    }

    fun viewsAction(){
        etxtSearchMovie.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                getCacheInstance().movieSearchResponse = null
                page = 1
                callEndpoints()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        ibtnSearchFilter.setOnClickListener{
            showFilterDialog()
        }
    }

    fun showFilterDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog?.dismiss()
        }
        dialog = Dialog(this.context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.filter_custom_dialog)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        val btnConfigDialogConnect = dialog?.findViewById<Button>(R.id.btnConfigDialogConnect)
        val ibtnDialogClose = dialog?.findViewById<ImageButton>(R.id.ibtnDialogClose)
        val etxtYear = dialog?.findViewById<EditText>(R.id.etxtYear)
        val switchAdult = dialog?.findViewById<Switch>(R.id.switchAdult)
        val spinnerLang = dialog?.findViewById<Spinner>(R.id.spinnerLang)

        etxtYear?.setText(movieYear.toString())
        switchAdult?.isChecked = isContainsAdult
        if(movieLang.equals("en"))
            spinnerLang?.setSelection(0)
        else
            spinnerLang?.setSelection(1)

        btnConfigDialogConnect?.setOnClickListener {
            try {
                movieYear = etxtYear?.text.toString().trim().toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this@SearchFragment.context, "Please enter the right year format!", Toast.LENGTH_LONG).show()
            }

            isContainsAdult = switchAdult?.isChecked!!
            movieLang = spinnerLang?.selectedItem.toString()

            getCacheInstance().movieSearchResponse = null
            page = 1

            callEndpoints()
            dialog?.dismiss()
        }
        ibtnDialogClose?.setOnClickListener { dialog?.dismiss() }

        dialog?.show()
    }
}