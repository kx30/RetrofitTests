package com.example.testtaskwebantapplicationkotlin.model

data class SomethingList (
    val data: ArrayList<PhotoContent>
)

data class PhotoContent (
    val image: Image,
    val id: Int,
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean
) {
    operator fun set(position: Int, value: List<PhotoContent>) {

    }
}

data class Image (
    val id: Int = 0,
    val contentUrl: String = ""
)
