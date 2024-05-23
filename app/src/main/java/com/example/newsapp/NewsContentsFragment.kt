package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient



/**
 * A simple [Fragment] subclass.
 * Use the [NewsContentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsContentsFragment : Fragment() {
    private val URL_KEY = "url"
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news_contents, container, false)

        val webView = view.findViewById<WebView>(R.id.web_view)

        // WebViewの設定
        val webSettings: WebSettings = webView.settings
//            webSettings.javaScriptEnabled = true // JavaScriptを有効にする

        // WebViewClientを設定してWebView内でリンクを開く
        webView.webViewClient = WebViewClient()

        // 指定したURLを読み込む
        webView.loadUrl(url!!)
        return view
    }
}