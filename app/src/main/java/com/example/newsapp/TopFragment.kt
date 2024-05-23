package com.example.newsapp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.newsapp.adapter.TopListAdapter
import com.example.newsapp.manager.GetNewsManager
import com.example.newsapp.util.ProgressDialogUtil
import kotlin.concurrent.thread

class TopFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val URL_KEY = "url"
    private var mActivity : Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mActivity = activity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)

        val manager = GetNewsManager()
        ProgressDialogUtil.show(mActivity!!)
        // ニュースリストをとってきて表示する
        val listView: ListView = view.findViewById(R.id.top_list_view)
        val listener = object : GetNewsManager.ICallback {
            override fun getFinished() {
                mActivity?.runOnUiThread {
                    // メインスレッドで実行する処理
                    ProgressDialogUtil.hide()
                    val adapter = TopListAdapter(mActivity!!, manager.getArticles())
                    listView.adapter = adapter
                }
            }

        }
        thread {
            manager.getNews(listener)
        }

        // ListViewのリスナー設定
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putString(URL_KEY, manager.getArticles()[position].url)
            val newsContentsFragment = NewsContentsFragment()
            newsContentsFragment.arguments = bundle
            transaction.replace(R.id.fragment_container, newsContentsFragment)
            transaction.addToBackStack(null) // 戻るボタンで戻れるようにする
            transaction.commit()
        }


        // Inflate the layout for this fragment
        return view
    }
}