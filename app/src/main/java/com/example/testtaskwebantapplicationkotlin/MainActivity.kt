package com.example.testtaskwebantapplicationkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.testtaskwebantapplicationkotlin.adapter.ImageAdapter
import com.example.testtaskwebantapplicationkotlin.model.Image
import com.example.testtaskwebantapplicationkotlin.model.PhotoContent
import com.example.testtaskwebantapplicationkotlin.retrofit.MyApi
import com.example.testtaskwebantapplicationkotlin.retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

open class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var jsonApi: MyApi
    private lateinit var compositeDisposable: CompositeDisposable

    private var currentPage: Int = 1
    private var totalPages: Int? = null
    private var lastVisibleItemPosition: Int? = null
    private var imageList: ArrayList<Image> = ArrayList()
    private var detailInformationList: ArrayList<PhotoContent> = ArrayList()
    private var isDownloaded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance

        jsonApi = retrofit.create(MyApi::class.java)
        compositeDisposable = CompositeDisposable()

        main_recycler_view.layoutManager = GridLayoutManager(this, 2)

        setRecyclerViewScrollListener()
        fetchData(currentPage)
    }

    private fun setRecyclerViewScrollListener() {
        main_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

    private fun downloadData(page: Int) {
        val disposable = jsonApi.getImages(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ jsonList ->
                jsonList.data.forEach {
                    imageList.add(it.image)
                    detailInformationList.add(it)
                }
                totalPages = jsonList.countOfPages
                displayData(imageList, detailInformationList)
            }, {
                it.printStackTrace()
            })
        compositeDisposable.addAll(disposable)
    }

    private fun displayData(images: List<Image>?, detailInformation: List<PhotoContent>) {
        val adapter = ImageAdapter(images!!, detailInformation)
        isRecyclerViewAdapterIsEmpty(adapter)
        changeProgressBarVisibility()
    }

    private fun isRecyclerViewAdapterIsEmpty(adapter: ImageAdapter) {
        if (main_recycler_view.adapter == null) {
            main_recycler_view.adapter = adapter
        }
        main_recycler_view.adapter!!.notifyDataSetChanged()
    }

    private fun changeProgressBarVisibility() {
        if (isDownloaded) {
            main_progress_bar.visibility = View.VISIBLE
        } else {
            main_progress_bar.visibility = View.GONE
        }
    }

    private fun setUpNewValuesAfterDownload() {
        isDownloaded = false
        currentPage++
    }

    private fun isAllPicturesHaveBeenDownloaded(): Boolean {
        return currentPage - 1 == totalPages
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}

