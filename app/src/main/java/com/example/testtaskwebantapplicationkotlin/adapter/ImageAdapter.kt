package com.example.testtaskwebantapplicationkotlin.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.testtaskwebantapplicationkotlin.R
import com.example.testtaskwebantapplicationkotlin.model.Image
import com.example.testtaskwebantapplicationkotlin.model.PhotoContent
import com.squareup.picasso.Picasso

class ImageAdapter(private var detailInformation: List<PhotoContent>) :
    RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, position: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(view.context).inflate(R.layout.image_item, view, false)

        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Picasso.get()
            .load("http://gallery.dev.webant.ru/media/" + detailInformation[position].image.contentUrl)
            .resize(500, 500)
            .into(holder.image)

        holder.imageId.text = detailInformation[position].id.toString()

        holder.setUpDetailInformation(detailInformation[position], detailInformation[position].image.contentUrl)
    }

    override fun getItemCount(): Int {
        return detailInformation.size
    }


}