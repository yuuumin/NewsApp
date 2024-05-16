package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.newsapp.R
import com.example.newsapp.data.Article

class TopListAdapter(context: Context, private val resource: Int, private val dataList: List<Article>)
    : ArrayAdapter<Article>(context, resource, dataList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = convertView ?: inflater.inflate(resource, parent, false)

        val titleText: TextView = view.findViewById(R.id.title)
        val descriptionText: TextView = view.findViewById(R.id.description)
        val publishedAtText: TextView = view.findViewById(R.id.published_at)
        val image: ImageView = view.findViewById(R.id.image)

        titleText.text = dataList[position].title
        descriptionText.text = dataList[position].description
        publishedAtText.text = dataList[position].publishedAt

        return super.getView(position, convertView, parent)
    }
}