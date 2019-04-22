package com.example.testtaskwebantapplicationkotlin.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.testtaskwebantapplicationkotlin.DetailActivity
import com.example.testtaskwebantapplicationkotlin.model.PhotoContent
import kotlinx.android.synthetic.main.image_item.view.*

class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val image = itemView.item_image_view
    val imageId = itemView.image_id

    var imageUrl: String? = null
    var detailName: String? = null
    var detailDescription: String? = null

    companion object {
        val DETAIL_INFORMATION_NAME_KEY = "DETAIL_INFORMATION_NAME"
        val DETAIL_INFORMATION_DESCRIPTION_KEY = "DETAIL_INFORMATION_DESCRIPTION"
        val DETAIL_INFORMATION_IMAGE_URL_KEY = "DETAIL_INFORMATION_IMAGE_URL"
    }

    fun setUpDetailInformation(detailInformation: PhotoContent, contentUrl: String) {
        imageUrl = contentUrl
        detailName = detailInformation.name
        detailDescription = detailInformation.description
    }

    init {
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra(DETAIL_INFORMATION_NAME_KEY, detailName)
            intent.putExtra(DETAIL_INFORMATION_DESCRIPTION_KEY, detailDescription)
            intent.putExtra(DETAIL_INFORMATION_IMAGE_URL_KEY, imageUrl)
            itemView.context.startActivity(intent)
        }
    }
}