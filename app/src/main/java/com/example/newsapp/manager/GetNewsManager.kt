package com.example.newsapp.manager

import com.example.newsapp.controller.ApiController
import com.example.newsapp.data.Article
import org.json.JSONObject

class GetNewsManager : ApiController.Callback {

    private val URL = "https://newsapi.org"
    private val API_KEY = "ed4948c245294642b0b9fce8b759a3e1"

    private var mArticles: MutableList<Article> = mutableListOf()

    fun getNews() {
        val endpoint = "/v2/top-headlines?country=jp&apiKey=$API_KEY"

        val header = mutableMapOf("X-Api-Key" to API_KEY)

        val controller = ApiController()
        controller.get(URL + endpoint, header, null)
    }

    fun getArticles() = mArticles

    override fun callback(result: String) {
        parseNewsJson(result)
    }

    private fun parseNewsJson(jsonStr: String) {
        val jsonObj = JSONObject(jsonStr)
        val articlesJsonArray = jsonObj.getJSONArray("articles")

        for (i in 0..articlesJsonArray.length()) {
            val articleObj = articlesJsonArray.getJSONObject(i)
            val title = articleObj.getString("title")
            val description = articleObj.getString("description")
            val url = articleObj.getString("url")
            val utlToImage = articleObj.getString("urlToImage")
            val publishedAt = articleObj.getString("publishedAt")
            val article = Article(title, description, url, utlToImage, publishedAt)
            mArticles.add(article)
        }
    }
}