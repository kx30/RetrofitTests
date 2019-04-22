package com.example.testtaskwebantapplicationkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.testtaskwebantapplicationkotlin.adapter.ImageViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageUrl: String? = intent.getStringExtra(ImageViewHolder.DETAIL_INFORMATION_IMAGE_URL_KEY)

        detail_name.text = intent.getStringExtra(ImageViewHolder.DETAIL_INFORMATION_NAME_KEY)
        detail_description.text = intent.getStringExtra(ImageViewHolder.DETAIL_INFORMATION_DESCRIPTION_KEY)
        Picasso.get()
            .load("http://gallery.dev.webant.ru/media/" + imageUrl)
            .into(detail_image)
    }
}
