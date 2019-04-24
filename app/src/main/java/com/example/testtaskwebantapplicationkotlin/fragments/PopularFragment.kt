package com.example.testtaskwebantapplicationkotlin.fragments

import com.example.testtaskwebantapplicationkotlin.model.PhotoContent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PopularFragment : BaseFragment<PhotoContent>() {

    override fun downloadData(page: Int) {
        val disposable = jsonApi.getPopularImages(page = page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ jsonList ->
                jsonList.data.forEach {
                    detailInformationList.add(it)
                }
                totalPages = jsonList.countOfPages
                displayData(detailInformationList)
            }, {
                it.printStackTrace()
            })
        compositeDisposable.addAll(disposable)
    }
}
