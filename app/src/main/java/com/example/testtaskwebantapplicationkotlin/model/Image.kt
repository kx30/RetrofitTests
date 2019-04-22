package com.example.testtaskwebantapplicationkotlin.model

data class JsonContent(
    val countOfPages: Int,
    val data: ArrayList<PhotoContent>
)

data class PhotoContent (
    val image: Image,
    val id: Int,
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean
)

data class Image (
    val id: Int = 0,
    val contentUrl: String = ""
)
