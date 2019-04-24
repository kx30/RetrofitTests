package com.example.testtaskwebantapplicationkotlin.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testtaskwebantapplicationkotlin.R
import com.example.testtaskwebantapplicationkotlin.adapter.ImageAdapter
import com.example.testtaskwebantapplicationkotlin.model.PhotoContent
import com.example.testtaskwebantapplicationkotlin.retrofit.Api
import com.example.testtaskwebantapplicationkotlin.retrofit.RetrofitClient
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment.*

abstract class BaseFragment<T>: Fragment() {

    lateinit var jsonApi: Api

    protected lateinit var compositeDisposable: CompositeDisposable

    protected var detailInformationList: ArrayList<T> = ArrayList()
    protected var currentPage: Int = 1
    protected var totalPages: Int? = null
    protected abstract fun downloadData(page: Int)

    private var lastVisibleItemPosition: Int? = null
    private var isDownloaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        val retrofit = RetrofitClient.instance
        jsonApi = retrofit.create(Api::class.java)
        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData(currentPage)
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        fragment_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                if (!isDownloaded && totalItemCount == lastVisibleItemPosition!! + 1 && !isAllPicturesHaveBeenDownloaded()) {
                    fetchData(currentPage)
                }
            }
        })
    }

    private fun fetchData(page: Int) {
        isDownloaded = true
        changeProgressBarVisibility()
        downloadData(page)
        setUpNewValuesAfterDownload()
        isAllPicturesHaveBeenDownloaded()
    }

    protected fun displayData(detailInformation: List<PhotoContent>) {
        val adapter = ImageAdapter(detailInformation)
        isRecyclerViewAdapterIsEmpty(adapter)
        changeProgressBarVisibility()
    }

    private fun isRecyclerViewAdapterIsEmpty(adapter: ImageAdapter) {
        if (fragment_recycler_view.adapter == null) {
            fragment_recycler_view.layoutManager = GridLayoutManager(activity, 2)
            fragment_recycler_view.adapter = adapter
        }
        fragment_recycler_view.adapter!!.notifyDataSetChanged()
    }

    private fun changeProgressBarVisibility() {
        if (isDownloaded) {
            fragment_progress_bar.visibility = View.VISIBLE
        } else {
            fragment_progress_bar.visibility = View.GONE
        }
    }

    private fun setUpNewValuesAfterDownload() {
        isDownloaded = false
        currentPage++
    }

    private fun isAllPicturesHaveBeenDownloaded(): Boolean {
        return currentPage - 1 == totalPages
    }

}