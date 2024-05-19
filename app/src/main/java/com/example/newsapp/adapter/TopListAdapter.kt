package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.newsapp.R
import com.example.newsapp.data.Article
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class TopListAdapter(context: Context, private val dataList: List<Article>)
    : BaseAdapter() {

    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): Any {
        return dataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: inflater.inflate(R.layout.news_list_item, parent, false)

        val titleText: TextView = view.findViewById(R.id.title)
        val publishedAtText: TextView = view.findViewById(R.id.published_at)
        val image: ImageView = view.findViewById(R.id.image)

        titleText.text = dataList[position].title
        publishedAtText.text = dataList[position].publishedAt
        if (dataList[position].urlToImage.isNullOrBlank()) {
            // 画像がない場合は表示しない
            image.visibility = View.GONE
        } else {
            Picasso.get()
                .load(dataList[position].urlToImage)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        image.visibility = View.VISIBLE
                    }

                    override fun onError(e: Exception?) {
                        // 画像がロードできない場合は表示しない
                        image.visibility = View.GONE
                    }

                })
        }

        return view
    }

}