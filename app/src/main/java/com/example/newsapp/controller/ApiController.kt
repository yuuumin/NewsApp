package com.example.newsapp.controller

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class ApiController {

    val TIMEOUT_MILLIS = 0

    interface Callback {
        fun callback(result: String)

        companion object {
            fun callback(result: String) {

            }
        }
    }

    fun get(endpoint: String, headers: Map<String, String>, params: Map<String, String>?) {

        val sb: StringBuffer = StringBuffer("")
        var httpConn: HttpURLConnection? = null
        var br: BufferedReader? = null
        var `is`: InputStream? = null
        var isr: InputStreamReader? = null

        try {
            val url = URL(endpoint)
            httpConn = url.openConnection() as HttpURLConnection
            httpConn.connectTimeout = TIMEOUT_MILLIS // 接続にかかる時間
            httpConn.readTimeout = TIMEOUT_MILLIS // データの読み込みにかかる時間
            httpConn.requestMethod = "GET" // HTTPメソッド
            httpConn.useCaches = false // キャッシュ利用
            httpConn.doOutput = false // リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.doInput = true // レスポンスのボディの受信を許可

            httpConn.connect()

            val responseCode = httpConn.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                `is` = httpConn.inputStream
                isr = InputStreamReader(`is`, "UTF-8")
                br = BufferedReader(isr)
                var line: String? = null
                while (br.readLine().also { line = it } != null) {
                    sb.append(line)
                }
            } else {
                // If responseCode is not HTTP_OK
            }
        } catch (e: IOException) {
            throw e
        } finally {
            Callback.callback(sb.toString())
        }
    }
}