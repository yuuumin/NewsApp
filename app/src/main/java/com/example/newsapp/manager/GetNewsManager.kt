package com.example.newsapp.manager

import android.util.Log
import com.example.newsapp.controller.ApiController
import com.example.newsapp.data.Article
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class GetNewsManager {

    private val URL = "https://newsapi.org"
    private val API_KEY = "ed4948c245294642b0b9fce8b759a3e1"

    private var mArticles: MutableList<Article> = mutableListOf()

    interface ICallback {
        fun getFinished()
    }

    fun getNews(listener: ICallback) {
        val endpoint = "/v2/top-headlines?country=jp&apiKey=$API_KEY"

        val header = mutableMapOf("X-Api-Key" to API_KEY)

        val controller = ApiController()
        val listener = object : ApiController.ICallback {
            override fun callback(result: String) {
                parseNewsJson(result)
                listener.getFinished()
            }

        }
        controller.get(listener, URL + endpoint, header, null)
    }

    fun getArticles() = mArticles

    private fun parseNewsJson(jsonStr: String) {
        val jsonObj = JSONObject(jsonStr)
        val articlesJsonArray = jsonObj.getJSONArray("articles")

        for (i in 0..<articlesJsonArray.length()) {
            val articleObj = articlesJsonArray.getJSONObject(i)
            val title = articleObj.getString("title")
            val description = articleObj.getString("description")
            val url = articleObj.getString("url")
            val utlToImage = articleObj.getString("urlToImage")

            val publishedAt = articleObj.getString("publishedAt")
            // ZonedDateTimeにパース
            val zonedPublishedAt = ZonedDateTime.parse(publishedAt)
            // 日本標準時（JST）に変換
            val jstPublishedAt = zonedPublishedAt.withZoneSameInstant(ZoneId.of("Asia/Tokyo"))
            // 年月日時分に変換
            val formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH時mm分")
            val formattedPublishedAt = jstPublishedAt.format(formatter)
            val article = Article(title, description, url, utlToImage, formattedPublishedAt)
            mArticles.add(article)
        }
        Log.d("aki", mArticles.toString())
    }
}