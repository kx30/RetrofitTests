package com.example.testtaskwebantapplicationkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
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

    private lateinit var jsonApi: MyApi
    private lateinit var compositeDisposable: CompositeDisposable

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance

        jsonApi = retrofit.create(MyApi::class.java)
        compositeDisposable = CompositeDisposable()

        recycler_view.layoutManager = GridLayoutManager(this, 2)
        fetchData()
    }

    private fun fetchData() {
        val disposable = jsonApi.getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ somethingList ->
                val imageList: ArrayList<Image> = ArrayList()
                val detailInformationList: ArrayList<PhotoContent> = ArrayList()
                somethingList.data.forEach {
                    imageList.add(it.image)
                    detailInformationList.add(it)
                }
                displayData(imageList, detailInformationList)
            }, {
                it.printStackTrace()
            })
        compositeDisposable.addAll(disposable)
    }

    private fun displayData(images: List<Image>?, detailInformation: List<PhotoContent>) {
        val adapter = ImageAdapter(images!!, detailInformation)
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}

