package com.example.testtaskwebantapplicationkotlin.model

data class PhotoContent (
    val image: Image,
    val id: Int,
    val name: String,
    val description: String,
    val new: Boolean,
    val popular: Boolean
)