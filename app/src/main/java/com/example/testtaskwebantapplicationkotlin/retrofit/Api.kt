package com.example.testtaskwebantapplicationkotlin.retrofit

import com.example.testtaskwebantapplicationkotlin.model.JsonContent
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("photos")
    fun getNewImages(
        @Query("limit") limit: Int = 10,
        @Query("new") new: Boolean = true,
        @Query("page") page: Int
    ): Single<JsonContent>

    @GET("photos")
    fun getPopularImages(
        @Query("limit") limit: Int = 10,
        @Query("popular") popular: Boolean = true,
        @Query("page") page: Int
    ): Single<JsonContent>
}